import sys
import time

import httpx

USER_AGENT = "hittiguess/0.1 (+https://hittiguess.com; contact@hittiguess.com)"

RATE_LIMIT_TARGET_UTILIZATION = 0.75  # stay comfortably inside each source's documented ceiling, not at its edge

MUSICBRAINZ_DELAY_SECONDS = 1.35  # documented hard limit is 1 req/sec; 1.35s paces to ~74% of that
# Confirmed against mediawiki.org/wiki/Wikimedia_APIs/Rate_limits: 10/min applies to
# requests with no identifying characteristics beyond IP, which is what an anonymous
# script is, the 200/min tier is specifically for browser-based traffic, not just a
# script with a descriptive User-Agent. 8.0s paces to exactly 75% of the 10/min ceiling.
# A Wikimedia bot-password account unlocks 200/min (any logged-in account, no approval
# needed) or 2,000/min (established editors), see ai/spikes/README.md.
WIKIDATA_DELAY_SECONDS = 8.0
# Discogs paces itself dynamically off the real X-Discogs-Ratelimit-* response headers
# (see discogs_spike.py's DiscogsRateLimiter) rather than a fixed delay, since Discogs
# is the one source that actually tells you your live quota usage.

DEFAULT_MAX_RETRIES = 4
DEFAULT_BASE_DELAY_SECONDS = 5.0
BACKOFF_MULTIPLIER = 2
RETRYABLE_HTTP_STATUS_CODES = (429, 503)
REQUEST_TIMEOUT_SECONDS = 10.0
YEAR_DIGIT_COUNT = 4

sys.stdout.reconfigure(encoding="utf-8", line_buffering=True)

# Keyed by "<status_code>" for an HTTP retry or the exception class name for a
# transport-level failure, incremented on every backoff, not just the final
# outcome, so a request that succeeds on its 3rd attempt still counts 2 retries.
RETRY_EVENT_COUNTS: dict[str, int] = {}


def get_with_backoff(url: str, *, params: dict | None = None, headers: dict | None = None,
                      max_retries: int = DEFAULT_MAX_RETRIES,
                      base_delay_seconds: float = DEFAULT_BASE_DELAY_SECONDS) -> httpx.Response:
    """GET with retry/backoff on rate-limit responses and transport-level
    failures. Honors Retry-After when the server sends one, otherwise backs
    off exponentially."""
    last_transport_error: httpx.TransportError | None = None
    for attempt_number in range(max_retries):
        try:
            response = httpx.get(url, params=params, headers=headers, timeout=REQUEST_TIMEOUT_SECONDS)
        except httpx.TransportError as transport_error:
            last_transport_error = transport_error
            wait_seconds = base_delay_seconds * (BACKOFF_MULTIPLIER**attempt_number)
            event_key = type(transport_error).__name__
            RETRY_EVENT_COUNTS[event_key] = RETRY_EVENT_COUNTS.get(event_key, 0) + 1
            print(
                f"  [{event_key}, backing off {wait_seconds:.1f}s "
                f"before retry {attempt_number + 1}/{max_retries}]"
            )
            time.sleep(wait_seconds)
            continue

        if response.status_code in RETRYABLE_HTTP_STATUS_CODES:
            retry_after_header = response.headers.get("Retry-After")
            minimum_wait_seconds = base_delay_seconds * (BACKOFF_MULTIPLIER**attempt_number)
            wait_seconds = (
                max(float(retry_after_header), minimum_wait_seconds) if retry_after_header else minimum_wait_seconds
            )
            event_key = str(response.status_code)
            RETRY_EVENT_COUNTS[event_key] = RETRY_EVENT_COUNTS.get(event_key, 0) + 1
            print(
                f"  [{response.status_code}, backing off {wait_seconds:.1f}s "
                f"before retry {attempt_number + 1}/{max_retries}]"
            )
            time.sleep(wait_seconds)
            continue
        response.raise_for_status()
        return response

    if last_transport_error is not None:
        raise last_transport_error
    response.raise_for_status()
    return response


def extract_year(date_str: str | None) -> int | None:
    """Reads the year from either MusicBrainz's plain "YYYY"/"YYYY-MM-DD"
    format or Wikidata's "+YYYY-MM-DDT00:00:00Z" format, comparing by year
    rather than by the raw date string, since a partial-precision date can
    otherwise sort incorrectly against a fully-precise one."""
    if not date_str:
        return None
    digits_only = date_str.lstrip("+-")
    year_digits = digits_only[:YEAR_DIGIT_COUNT]
    return int(year_digits) if year_digits.isdigit() else None
