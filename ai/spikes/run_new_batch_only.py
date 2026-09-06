"""Fetches and caches all four sources for just the 21 new songs in
mainstream_test_songs.py, reusing each source's existing per-song lookup
logic. The other 49 songs already have cached data from earlier runs,
re-fetching them here would waste real API budget for no new information.
See spikes/README.md.

Usage: python spikes/run_new_batch_only.py
"""

import time

import discogs_spike
import musicbrainz_spike
import response_cache
import wikidata_spike
import wikipedia_spike
from _shared import RETRY_EVENT_COUNTS, extract_year
from mainstream_test_songs import GROUND_TRUTH, MAINSTREAM_TEST_SONGS
from run_full_discogs import run_one as discogs_run_one
from run_full_musicbrainz import run_one as musicbrainz_run_one
from run_full_wikidata import run_one as wikidata_run_one
from run_full_wikipedia import _lookup as wikipedia_lookup

if __name__ == "__main__":
    run_started_at = time.perf_counter()
    mb_correct = dg_correct = wd_correct = 0

    for song_index, (title, artist, album, tier, note) in enumerate(MAINSTREAM_TEST_SONGS, start=1):
        acceptable_years = GROUND_TRUTH[title]
        print(f"[{song_index}/{len(MAINSTREAM_TEST_SONGS)}] [{tier}] {title!r} by {artist!r}")

        mb_year = musicbrainz_run_one(title, artist, album)
        mb_outcome = "correct" if mb_year in acceptable_years else f"WRONG/no-answer ({mb_year})"
        if mb_year in acceptable_years:
            mb_correct += 1
        print(f"    MusicBrainz: {mb_year} [{mb_outcome}]")

        dg_year = discogs_run_one(title, artist, album)
        dg_outcome = "correct" if dg_year in acceptable_years else f"WRONG/no-answer ({dg_year})"
        if dg_year in acceptable_years:
            dg_correct += 1
        print(f"    Discogs: {dg_year} [{dg_outcome}]")

        wd_year = wikidata_run_one(title, artist, album)
        wd_outcome = "correct" if wd_year in acceptable_years else f"WRONG/no-answer ({wd_year})"
        if wd_year in acceptable_years:
            wd_correct += 1
        print(f"    Wikidata: {wd_year} [{wd_outcome}]")

        track_entry = wikipedia_lookup(title, artist, "track")
        album_entry = wikipedia_lookup(album, artist, "album") if album else None
        wiki_entries = [entry for entry in (track_entry, album_entry) if entry is not None]
        response_cache.save("wikipedia", title, artist, wiki_entries)
        print(f"    Wikipedia: cached {len(wiki_entries)} extract(s)")

    total = len(MAINSTREAM_TEST_SONGS)
    total_seconds = time.perf_counter() - run_started_at
    print("\n=== New-batch fetch results (structured sources only, Wikipedia needs extraction separately) ===")
    print(f"MusicBrainz: {mb_correct}/{total} correct")
    print(f"Discogs: {dg_correct}/{total} correct")
    print(f"Wikidata: {wd_correct}/{total} correct")
    print(f"Total wall-clock time: {total_seconds:.1f}s")
    if RETRY_EVENT_COUNTS:
        print(f"Retry/backoff events: {dict(RETRY_EVENT_COUNTS)}")
