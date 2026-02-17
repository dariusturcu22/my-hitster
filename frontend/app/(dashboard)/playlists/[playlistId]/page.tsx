import { AppSidebar } from "@/components/app-sidebar";
import { SiteHeader } from "@/components/site-header";
import { SidebarInset, SidebarProvider } from "@/components/shadcn/sidebar";
import React from "react";

import songsData from "./data.json";
import playlistsData from "@/app/data/playlists.json";
import PlaylistContent from "./PlaylistContent";

export default async function PlaylistPage({
  params,
}: {
  params: Promise<{ playlistId: string }>;
}) {
  const { playlistId } = await params;
  const id = parseInt(playlistId);

  const currentPlaylist = playlistsData.find((playlist) => playlist.id === id);

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
        currentPlaylistId={id}
      />
      <SidebarInset>
        <SiteHeader title={currentPlaylist?.name || "Playlist"} />

        <PlaylistContent songs={songsData} playlistId={id} />
      </SidebarInset>
    </SidebarProvider>
  );
}
