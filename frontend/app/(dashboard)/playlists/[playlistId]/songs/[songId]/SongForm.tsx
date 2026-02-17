"use client";

import React, { useState } from "react";
import { Input } from "@/components/shadcn/input";
import { Label } from "@/components/shadcn/label";
import { Button } from "@/components/shadcn/button";
import { IconExternalLink } from "@tabler/icons-react";
import Link from "next/link";

export function SongForm({ song, backPath }: { song: any; backPath: string }) {
  const [formData, setFormData] = useState(song);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { id, value } = e.target;
    setFormData((prev: any) => ({ ...prev, [id]: value }));
  };

  const handleColorChange = (id: string, value: string) => {
    setFormData((prev: any) => ({ ...prev, [id]: value }));
  };

  return (
    <div className="mx-auto w-full max-w-xl flex flex-col gap-6">
      <div className="grid gap-4">
        <div className="flex justify-center">
          <div className="aspect-video w-full max-w-xs overflow-hidden rounded-lg border bg-muted shadow-sm">
            <iframe
              width="100%"
              height="100%"
              src={`https://www.youtube.com/embed/${formData.youtubeId}`}
              title="YouTube video player"
              allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
              allowFullScreen
            ></iframe>
          </div>
        </div>
        <div className="grid gap-2">
          <Label htmlFor="youtubeId">YouTube ID</Label>
          <div className="flex gap-2">
            <Input
              id="youtubeId"
              value={formData.youtubeId}
              onChange={handleChange}
              className="text-left"
            />
            <Button variant="outline" size="icon" asChild>
              <a
                href={`https://www.youtube.com/watch?v=${formData.youtubeId}`}
                target="_blank"
                rel="noopener noreferrer"
              >
                <IconExternalLink className="size-4" />
              </a>
            </Button>
          </div>
        </div>
      </div>

      <div className="grid gap-2">
        <Label htmlFor="title">Title</Label>
        <Input
          id="title"
          value={formData.title}
          onChange={handleChange}
          className="text-left"
        />
      </div>

      <div className="grid gap-2">
        <Label htmlFor="artist">Artist</Label>
        <Input
          id="artist"
          value={formData.artist}
          onChange={handleChange}
          className="text-left"
        />
      </div>

      <div className="grid gap-2">
        <Label htmlFor="releaseYear">Release Year</Label>
        <Input
          id="releaseYear"
          type="number"
          value={formData.releaseYear}
          onChange={handleChange}
          className="text-left"
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
            {formData.artist}
          </div>

          <div
            className="font-medium tracking-tighter"
            style={{ fontSize: "62px" }}
          >
            {formData.releaseYear}
          </div>

          <div
            className="mb-2 text-center italic font-light leading-tight"
            style={{ fontSize: "15px" }}
          >
            {formData.title}
          </div>
        </div>
      </div>

      <div className="flex gap-3 justify-center">
        <Button className="px-10">Save Changes</Button>
        <Button variant="outline" className="px-10" asChild>
          <Link href={backPath}>Cancel</Link>
        </Button>
      </div>
    </div>
  );
}
