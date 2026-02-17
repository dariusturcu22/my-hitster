import { AppSidebar } from "@/components/app-sidebar";
import { SiteHeader } from "@/components/site-header";
import { SidebarInset, SidebarProvider } from "@/components/shadcn/sidebar";
import { Button } from "@/components/shadcn/button";
import { IconArrowLeft } from "@tabler/icons-react";
import Link from "next/link";
import React from "react";
import { SongForm } from "./SongForm";

import songsData from "../../data.json";
import playlistsData from "@/app/data/playlists.json";

export default async function SongDetailPage({
  params,
}: {
  params: Promise<{ playlistId: string; songId: string }>;
}) {
  const { playlistId, songId } = await params;
  const playlistIdNumber = parseInt(playlistId);
  const songIdNumber = parseInt(songId);

  const song = songsData.find(
    (s) => s.id === songIdNumber && s.playlistId === playlistIdNumber,
  );

  if (!song) return <div className="p-8">Song not found</div>;

  const backPath = `/playlists/${playlistId}`;

  return (
    <SidebarProvider
      style={
        {
          "--sidebar-width": "calc(var(--spacing) * 72)",
          "--header-height": "calc(var(--spacing) * 12)",
        } as React.CSSProperties
      }
    >
      <AppSidebar
        variant="inset"
        playlists={playlistsData}
        currentPlaylistId={playlistIdNumber}
      />
      <SidebarInset>
        <SiteHeader title={song.title} />

        <div className="flex flex-1 flex-col p-4 md:p-6">
          <div className="mb-6">
            <Button variant="ghost" size="sm" asChild>
              <Link href={backPath}>
                <IconArrowLeft className="size-4 mr-2" />
                Back to Playlist
              </Link>
            </Button>
          </div>

          <SongForm song={song} backPath={backPath} />

          <div className="text-center text-[10px] text-muted-foreground mt-4">
            Added by {song.addedBy.name}
          </div>
        </div>
      </SidebarInset>
    </SidebarProvider>
  );
}
