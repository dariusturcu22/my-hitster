"use client";

import { useState } from "react";
import { DataTable } from "@/components/data-table";

interface User {
  id: number;
  name: string;
  email: string;
}

export interface Song {
  id: number;
  title: string;
  artist: string;
  releaseYear: number;
  youtubeId: string;
  gradientColor1: string;
  gradientColor2: string;
  playlistId: number;
  addedBy: User;
}

interface PlaylistContentProps {
  songs: Song[];
  playlistId: number;
}

export default function PlaylistContent({
  songs,
  playlistId,
}: PlaylistContentProps) {
  const [songList, setSongList] = useState<Song[]>(songs);

  const handleDeleteSong = (songIdToRemove: number): void => {
    setSongList((currentSongs) =>
      currentSongs.filter((song) => song.id !== songIdToRemove),
    );
  };

  return (
    <div className="flex flex-1 flex-col">
      <div className="@container/main flex flex-1 flex-col gap-2">
        <div className="flex flex-col gap-4 py-4 md:gap-6 md:py-6">
          <DataTable
            data={songList}
            playlistId={playlistId}
            onDelete={handleDeleteSong}
          />
        </div>
      </div>
    </div>
  );
}
