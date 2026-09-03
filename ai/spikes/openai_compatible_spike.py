"""Spike: exercise any OpenAI-compatible chat completions endpoint against
the shared LlmExtractionResult schema, covering the story 20 candidates that
expose an OpenAI-compatible API (Zhipu, Groq, DeepInfra) plus the OpenAI
benchmark models (gpt-5.1, gpt-5-mini, gpt-5-nano) themselves, so all of them
go through the same code path for a fair comparison. See spikes/README.md.

Usage: python spikes/openai_compatible_spike.py <provider> <model> "<title>" "<artist>"
Example: python spikes/openai_compatible_spike.py groq openai/gpt-oss-20b "Roygbiv" "Boards of Canada"
"""

import sys
from pathlib import Path

from dotenv import dotenv_values
from openai import BadRequestError, OpenAI

from llm_schemas import LlmExtractionResult

_env = dotenv_values(Path(__file__).resolve().parent.parent / ".env")

STRUCTURED_OUTPUT_TEMPERATURE = 0.1

# base_url=None means the provider's own default (OpenAI itself).
PROVIDERS: dict[str, dict[str, str | None]] = {
    "openai": {"base_url": None, "api_key_env": "OPENAI_API_KEY"},
    "zhipu": {"base_url": "https://api.z.ai/api/paas/v4/", "api_key_env": "ZHIPU_API_KEY"},
    "groq": {"base_url": "https://api.groq.com/openai/v1", "api_key_env": "GROQ_API_KEY"},
    "deepinfra": {"base_url": "https://api.deepinfra.com/v1/openai", "api_key_env": "DEEPINFRA_API_KEY"},
    # llama-server (ai/local-llm/) takes no API key; any non-empty string satisfies the SDK.
    "llamacpp": {"base_url": "http://127.0.0.1:8090/v1", "api_key_env": None},
}
LLAMACPP_PLACEHOLDER_API_KEY = "not-needed"


def build_client(provider: str) -> OpenAI:
    config = PROVIDERS[provider]
    if config["api_key_env"] is None:
        api_key = LLAMACPP_PLACEHOLDER_API_KEY
    else:
        api_key = _env.get(config["api_key_env"])
        if not api_key:
            raise ValueError(f"{config['api_key_env']} is not set in ai/.env")
    return OpenAI(api_key=api_key, base_url=config["base_url"])


def extract(provider: str, model: str, prompt: str) -> LlmExtractionResult:
    client = build_client(provider)
    request_kwargs = {
        "model": model,
        "messages": [{"role": "user", "content": prompt}],
        "response_format": LlmExtractionResult,
    }
    try:
        completion = client.chat.completions.parse(temperature=STRUCTURED_OUTPUT_TEMPERATURE, **request_kwargs)
    except BadRequestError as bad_request_error:
        # Some models (observed on gpt-5-nano) only accept the default temperature
        # and reject any explicit value, including the one matching their default.
        if bad_request_error.body and bad_request_error.body.get("param") == "temperature":
            completion = client.chat.completions.parse(**request_kwargs)
        else:
            raise

    parsed = completion.choices[0].message.parsed
    if parsed is None:
        raw_content = completion.choices[0].message.content
        raise ValueError(
            f"{provider}/{model} response did not match the expected schema. Raw content: {raw_content!r}"
        )
    return parsed


def build_smoke_test_prompt(title: str, artist: str) -> str:
    return (
        "You are a music metadata analyst. Given only this song title and artist, "
        "with no external source data, extract the cleaned title, artist, your best-guess "
        "original release year (null if you genuinely don't know), a confidence level "
        "(high/medium/low), and your reasoning. The response fields are enforced by a JSON "
        f"schema, do not describe the JSON shape yourself.\n\nTitle: {title}\nArtist: {artist}"
    )


if __name__ == "__main__":
    if len(sys.argv) != 5:
        print('usage: openai_compatible_spike.py <provider> <model> "<title>" "<artist>"')
        print(f"providers: {', '.join(PROVIDERS)}")
        sys.exit(1)

    _script_path, provider_argument, model_argument, title_argument, artist_argument = sys.argv
    result = extract(provider_argument, model_argument, build_smoke_test_prompt(title_argument, artist_argument))
    print(result.model_dump_json(indent=2))
