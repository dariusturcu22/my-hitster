package org.dariusturcu.backend.util;

import java.util.List;
import java.util.Map;

public class MetadataPromptBuilder {
    private final static int MAX_DESCRIPTION_LENGTH = 1500;

    public static String build(Map<String, Object> allData) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("You are an expert music historian and metadata analyst. Your task is to find the ORIGINAL RELEASE YEAR of this song.\n\n");
        prompt.append("=== CRITICAL RULES ===\n");
        prompt.append("1. Return ONLY the YEAR (YYYY format), NOT full dates\n");
        prompt.append("2. Carefully read the YouTube description - it often contains the actual release year!\n");
        prompt.append("3. Look for artist names in channel names and descriptions\n");
        prompt.append("4. YouTube upload year is NOT the release year - ignore it unless nothing else exists\n");
        prompt.append("5. For soundtracks/game music/anime: find the game/show release year, NOT the upload year\n");
        prompt.append("6. Cross-reference multiple sources - if they disagree, explain why\n\n");

        appendYouTubeData(prompt, allData);
        appendMusicBrainzData(prompt, allData);
        appendWikipediaData(prompt, allData);
        appendGeniusData(prompt, allData);
        appendTaskInstructions(prompt);

        return prompt.toString();
    }

    @SuppressWarnings("unchecked")
    private static void appendYouTubeData(StringBuilder prompt, Map<String, Object> allData) {
        Map<String, String> youtubeData = (Map<String, String>) allData.get("youtube");
        prompt.append("=== YOUTUBE VIDEO DATA ===\n");
        prompt.append(String.format("Video Title: %s\n", youtubeData.get("video_title")));
        prompt.append(String.format("Channel Name: %s\n", youtubeData.get("channel_title")));
        prompt.append(String.format("Upload Year: %s (THIS IS NOT THE RELEASE YEAR!)\n", youtubeData.get("upload_year")));

        String description = youtubeData.get("description");
        if (description != null && !description.isEmpty()) {
            String limitedDescription = description.length() > MAX_DESCRIPTION_LENGTH
                    ? description.substring(0, MAX_DESCRIPTION_LENGTH) + "..."
                    : description;
            prompt.append("\nVideo Description (READ CAREFULLY):\n---\n");
            prompt.append(limitedDescription);
            prompt.append("\n---\n\n");
        } else {
            prompt.append("\nVideo Description: (none)\n\n");
        }
    }

    @SuppressWarnings("unchecked")
    private static void appendMusicBrainzData(StringBuilder prompt, Map<String, Object> allData) {
        List<Map<String, String>> musicBrainzResults = (List<Map<String, String>>) allData.get("musicbrainz");
        if (musicBrainzResults != null && !musicBrainzResults.isEmpty()) {
            prompt.append("=== MUSICBRAINZ DATABASE (Most Authoritative) ===\n");
            for (int i = 0; i < Math.min(5, musicBrainzResults.size()); i++) {
                Map<String, String> result = musicBrainzResults.get(i);
                String releaseDate = result.get("release_date");
                String year = !releaseDate.equals("unknown") && releaseDate.length() >= 4
                        ? releaseDate.substring(0, 4)
                        : "unknown";
                prompt.append(String.format("%d. \"%s\" by %s - Year: %s (Match Score: %s/100)\n",
                        i + 1, result.get("title"), result.get("artist"), year, result.get("score")));
            }
            prompt.append("\n");
        }
    }

    @SuppressWarnings("unchecked")
    private static void appendWikipediaData(StringBuilder prompt, Map<String, Object> allData) {
        Map<String, String> wikipediaData = (Map<String, String>) allData.get("wikipedia");
        if (wikipediaData != null) {
            prompt.append("=== WIKIPEDIA ===\n");
            prompt.append(String.format("Page: %s\n", wikipediaData.get("title")));
            prompt.append(String.format("Info: %s\n\n", wikipediaData.get("release_info")));
        }
    }

    @SuppressWarnings("unchecked")
    private static void appendGeniusData(StringBuilder prompt, Map<String, Object> allData) {
        Map<String, String> geniusData = (Map<String, String>) allData.get("genius");
        if (geniusData != null) {
            prompt.append("=== GENIUS ===\n");
            String geniusRelease = geniusData.get("release_date");
            String geniusYear = !geniusRelease.equals("unknown") && geniusRelease.length() >= 4
                    ? geniusRelease.substring(0, 4)
                    : "unknown";
            prompt.append(String.format("\"%s\" by %s - Year: %s\n\n",
                    geniusData.get("title"), geniusData.get("artist"), geniusYear));
        }
    }

    private static void appendTaskInstructions(StringBuilder prompt) {
        prompt.append("=== YOUR ANALYSIS TASK ===\n");
        prompt.append("Return ONLY this JSON (no markdown, no extra text):\n\n");
        prompt.append("{\n");
        prompt.append("  \"artist\": \"actual artist name\",\n");
        prompt.append("  \"title\": \"actual song title (cleaned)\",\n");
        prompt.append("  \"release_year\": \"YYYY format ONLY, or 'unknown'\",\n");
        prompt.append("  \"confidence\": \"high/medium/low\",\n");
        prompt.append("  \"source\": \"where you found the year\",\n");
        prompt.append("  \"reasoning\": \"explain in 1-2 sentences\"\n");
        prompt.append("}\n\n");
        prompt.append("CONFIDENCE GUIDELINES:\n");
        prompt.append("- high: MusicBrainz score >85 OR year explicitly stated OR multiple sources agree\n");
        prompt.append("- medium: MusicBrainz score 70-85 OR single reliable source\n");
        prompt.append("- low: Only YouTube data OR conflicting sources\n");
    }
}