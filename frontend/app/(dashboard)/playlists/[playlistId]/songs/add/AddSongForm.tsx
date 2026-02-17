"use client";

import React, { useState } from "react";
import { Input } from "@/components/shadcn/input";
import { Label } from "@/components/shadcn/label";
import { Button } from "@/components/shadcn/button";
import {
  IconExternalLink,
  IconLoader2,
  IconSparkles,
  IconCheck,
} from "@tabler/icons-react";
import Link from "next/link";

type Step = "youtube" | "preview" | "details";

interface SongDetails {
  title: string;
  artist: string;
  releaseYear: string;
  gradientColor1: string;
  gradientColor2: string;
}

function extractYoutubeId(input: string): string | null {
  // Handle plain ID (no slashes or dots)
  if (/^[a-zA-Z0-9_-]{11}$/.test(input.trim())) return input.trim();

  try {
    const url = new URL(input);
    if (url.searchParams.get("v")) return url.searchParams.get("v");
    if (url.hostname === "youtu.be") return url.pathname.slice(1).split("?")[0];
    if (url.pathname.startsWith("/embed/"))
      return url.pathname.split("/embed/")[1].split("?")[0];
  } catch {
    // not valid
  }
  return null;
}

export function AddSongForm({ backPath }: { backPath: string }) {
  const [step, setStep] = useState<Step>("youtube");
  const [youtubeInput, setYoutubeInput] = useState("");
  const [youtubeId, setYoutubeId] = useState<string | null>(null);
  const [youtubeError, setYoutubeError] = useState("");
  const [isLoadingAI, setIsLoadingAI] = useState(false);
  const [aiError, setAiError] = useState("");
  const [formData, setFormData] = useState<SongDetails>({
    title: "",
    artist: "",
    releaseYear: "",
    gradientColor1: "#8B5CF6",
    gradientColor2: "#EC4899",
  });

  const handleCheckYoutube = () => {
    setYoutubeError("");
    const id = extractYoutubeId(youtubeInput);
    if (!id) {
      setYoutubeError(
        "Couldn't extract a YouTube ID from that link. Try a full URL or just the video ID.",
      );
      return;
    }
    setYoutubeId(id);
    setStep("preview");
  };

  const handleGetDetails = async () => {
    setIsLoadingAI(true);
    setAiError("");

    try {
      // TODO replace with real orval generated query
      const response = await fetch("", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({}),
      });

      const data = await response.json();
      const text = data.content?.[0]?.text ?? "";

      const clean = text.replace(/```json|```/g, "").trim();
      const parsed = JSON.parse(clean);

      setFormData({
        title: parsed.title ?? "",
        artist: parsed.artist ?? "",
        releaseYear: parsed.releaseYear ?? "",
        gradientColor1: parsed.gradientColor1 ?? "#8B5CF6",
        gradientColor2: parsed.gradientColor2 ?? "#EC4899",
      });
      setStep("details");
    } catch {
      setAiError("Failed to get details. You can still fill them in manually.");
      setStep("details");
    } finally {
      setIsLoadingAI(false);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { id, value } = e.target;
    setFormData((prev) => ({ ...prev, [id]: value }));
  };

  const handleColorChange = (id: string, value: string) => {
    setFormData((prev) => ({ ...prev, [id]: value }));
  };

  return (
    <div className="mx-auto w-full max-w-xl flex flex-col gap-6">
      <div className="grid gap-3">
        <Label htmlFor="youtubeUrl">YouTube Link</Label>
        <div className="flex gap-2">
          <Input
            id="youtubeUrl"
            placeholder="https://youtube.com/watch?v=... or video ID"
            value={youtubeInput}
            onChange={(e) => {
              setYoutubeInput(e.target.value);
              setYoutubeError("");
            }}
            onKeyDown={(e) => e.key === "Enter" && handleCheckYoutube()}
          />
          <Button
            variant="outline"
            onClick={handleCheckYoutube}
            className="shrink-0"
          >
            {step !== "youtube" ? (
              <IconCheck className="size-4 text-green-500" />
            ) : (
              "Check"
            )}
          </Button>
        </div>
        {youtubeError && (
          <p className="text-sm text-destructive">{youtubeError}</p>
        )}
      </div>

      {(step === "preview" || step === "details") && youtubeId && (
        <div className="grid gap-4 animate-in fade-in slide-in-from-bottom-2 duration-300">
          <div className="flex justify-center">
            <div className="aspect-video w-full max-w-xs overflow-hidden rounded-lg border bg-muted shadow-sm">
              <iframe
                width="100%"
                height="100%"
                src={`https://www.youtube.com/embed/${youtubeId}`}
                title="YouTube video player"
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                allowFullScreen
              />
            </div>
          </div>

          <div className="flex gap-2 justify-center">
            <Button variant="outline" size="icon" asChild>
              <a
                href={`https://www.youtube.com/watch?v=${youtubeId}`}
                target="_blank"
                rel="noopener noreferrer"
              >
                <IconExternalLink className="size-4" />
              </a>
            </Button>

            {step === "preview" && (
              <Button
                onClick={handleGetDetails}
                disabled={isLoadingAI}
                className="gap-2"
              >
                {isLoadingAI ? (
                  <>
                    <IconLoader2 className="size-4 animate-spin" />
                    Fetching details...
                  </>
                ) : (
                  <>
                    <IconSparkles className="size-4" />
                    Get Details with AI
                  </>
                )}
              </Button>
            )}

            {step === "details" && (
              <Button
                variant="outline"
                onClick={handleGetDetails}
                disabled={isLoadingAI}
                className="gap-2"
              >
                {isLoadingAI ? (
                  <IconLoader2 className="size-4 animate-spin" />
                ) : (
                  <IconSparkles className="size-4" />
                )}
                Retry AI
              </Button>
            )}
          </div>

          {aiError && (
            <p className="text-sm text-destructive text-center">{aiError}</p>
          )}
        </div>
      )}

      {step === "details" && (
        <div className="flex flex-col gap-6 animate-in fade-in slide-in-from-bottom-2 duration-300">
          <div className="grid gap-2">
            <Label htmlFor="title">Title</Label>
            <Input id="title" value={formData.title} onChange={handleChange} />
          </div>

          <div className="grid gap-2">
            <Label htmlFor="artist">Artist</Label>
            <Input
              id="artist"
              value={formData.artist}
              onChange={handleChange}
            />
          </div>

          <div className="grid gap-2">
            <Label htmlFor="releaseYear">Release Year</Label>
            <Input
              id="releaseYear"
              type="number"
              value={formData.releaseYear}
              onChange={handleChange}
            />
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div className="flex items-center gap-3">
              <Input
                type="color"
                value={formData.gradientColor1}
                onChange={(e) =>
                  handleColorChange("gradientColor1", e.target.value)
                }
                className="size-8 p-0 border-none rounded shadow-sm shrink-0 cursor-pointer overflow-hidden"
              />
              <div className="grid gap-1 w-full">
                <Label className="text-[10px] uppercase">Color 1</Label>
                <Input
                  value={formData.gradientColor1}
                  onChange={(e) =>
                    handleColorChange("gradientColor1", e.target.value)
                  }
                  className="h-8 font-mono text-xs"
                />
              </div>
            </div>
            <div className="flex items-center gap-3">
              <Input
                type="color"
                value={formData.gradientColor2}
                onChange={(e) =>
                  handleColorChange("gradientColor2", e.target.value)
                }
                className="size-8 p-0 border-none rounded shadow-sm shrink-0 cursor-pointer overflow-hidden"
              />
              <div className="grid gap-1 w-full">
                <Label className="text-[10px] uppercase">Color 2</Label>
                <Input
                  value={formData.gradientColor2}
                  onChange={(e) =>
                    handleColorChange("gradientColor2", e.target.value)
                  }
                  className="h-8 font-mono text-xs"
                />
              </div>
            </div>
          </div>

          <div className="flex flex-col items-center py-4">
            <div
              className="relative aspect-square w-50 rounded-lg shadow-xl flex flex-col items-center justify-between p-4 text-black overflow-hidden"
              style={{
                background: `linear-gradient(to bottom, ${formData.gradientColor1}, ${formData.gradientColor2})`,
                fontFamily: "'Kanit', sans-serif",
              }}
            >
              <div
                className="mt-2 text-center font-normal leading-tight"
                style={{ fontSize: "15px" }}
              >
                {formData.artist || "Artist"}
              </div>
              <div
                className="font-medium tracking-tighter"
                style={{ fontSize: "62px" }}
              >
                {formData.releaseYear || "Year"}
              </div>
              <div
                className="mb-2 text-center italic font-light leading-tight"
                style={{ fontSize: "15px" }}
              >
                {formData.title || "Title"}
              </div>
            </div>
          </div>

          <div className="flex gap-3 justify-center">
            <Button className="px-10">Add Song</Button>
            <Button variant="outline" className="px-10" asChild>
              <Link href={backPath}>Cancel</Link>
            </Button>
          </div>
        </div>
      )}
    </div>
  );
}
