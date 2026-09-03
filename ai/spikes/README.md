# Metadata source spikes

Throwaway scripts for testing MusicBrainz, Discogs, and Wikidata's live APIs
against real songs, deciding the call/reconcile shape in
[`TASKS.md`'s spike entry](../../docs/TASKS.md) before writing the real
`ai/app/metadata/sources/musicbrainz.py` and `wikidata.py`. Not imported by
the AI microservice, not wired into `service.py`'s pipeline. YouTube's
own spike script exists for comparison only, `youtube.py` is already live.

Run from the `ai/` directory with the project's virtualenv:

```
.venv/Scripts/python.exe spikes/musicbrainz_spike.py "Roygbiv" "Boards of Canada"
.venv/Scripts/python.exe spikes/discogs_spike.py "Roygbiv" "Boards of Canada"
.venv/Scripts/python.exe spikes/wikidata_spike.py "Roygbiv" "Boards of Canada"
.venv/Scripts/python.exe spikes/youtube_spike.py dQw4w9WgXcQ
```

Discogs needs `DISCOGS_CONSUMER_KEY`/`DISCOGS_CONSUMER_SECRET` and YouTube
needs `YOUTUBE_API_KEY` in `ai/.env`, same as the real app. MusicBrainz and
Wikidata need no key.

## Story 20: local/cheap LLM candidates

Same throwaway-script treatment, testing each shortlisted candidate against
the shared `LlmExtractionResult` schema (`llm_schemas.py`) so results are
comparable. `openai_compatible_spike.py` covers every candidate that exposes
an OpenAI-compatible endpoint (OpenAI itself, Zhipu, Groq, DeepInfra):

```
.venv/Scripts/python.exe spikes/openai_compatible_spike.py openai gpt-5-nano "Roygbiv" "Boards of Canada"
.venv/Scripts/python.exe spikes/openai_compatible_spike.py groq openai/gpt-oss-20b "Roygbiv" "Boards of Canada"
.venv/Scripts/python.exe spikes/openai_compatible_spike.py deepinfra meta-llama/Meta-Llama-3.1-8B-Instruct-Turbo "Roygbiv" "Boards of Canada"
```

AWS Bedrock's Converse API has no OpenAI-style `response_format`, so Nova
Micro gets its own script using forced tool use instead:

```
.venv/Scripts/python.exe spikes/bedrock_spike.py "Roygbiv" "Boards of Canada"
```

Needs `ZHIPU_API_KEY`, `GROQ_API_KEY`, `DEEPINFRA_API_KEY`, and
`AWS_ACCESS_KEY_ID`/`AWS_SECRET_ACCESS_KEY`/`AWS_REGION` in `ai/.env`.

**Zhipu GLM-4.5-Flash confirmed NOT to clear the structured-output bar**,
despite Z.ai's own docs claiming JSON-schema support. Tested twice against
the live API: `response_format: {"type": "json_schema", ...}` is silently
ignored, the model free-writes prose with a markdown-fenced JSON block
instead (wrong field names included); forced tool-calling produces
corrupted output (a malformed float literal, a leaked `</tool_call>` tag
inside the arguments string). Both are the "best-effort JSON" failure mode
`CLAUDE.md`'s rule against regex-parsing exists to catch, not the hard
guarantee the docs describe. Dropped from the shortlist pending a retest of
GLM-4.7-Flash specifically, which may not share the same behavior.
