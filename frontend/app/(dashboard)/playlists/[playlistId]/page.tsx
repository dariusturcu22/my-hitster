"use client";

import { AppSidebar } from "@/components/app-sidebar";
import { SiteHeader } from "@/components/site-header";
import { SidebarInset, SidebarProvider } from "@/components/shadcn/sidebar";
import React, { use } from "react";

import PlaylistContent from "./PlaylistContent";
import { useGetUserPlaylists } from "@/api/generated/user-management/user-management";

interface PageProps {
  params: Promise<{ playlistId: string }>;
}

export default function PlaylistPage({ params }: PageProps) {
  const { playlistId: rawId } = use(params);
  const playlistId = parseInt(rawId);
  const { data: playlists, isLoading, isError, error } = useGetUserPlaylists();

  if (isLoading) {
    return <div>Loading playlists...</div>;
  }

  if (isError) {
    return <div>Failed to load playlists</div>;
  }

  if (!playlists) {
    return <div>You are not a part of any playlist</div>;
  }

  const currentPlaylist = playlists.find(
    (playlist) => playlist.id === playlistId,
  );

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
        playlists={playlists}
        currentPlaylistId={currentPlaylist?.id}
      />
      <SidebarInset>
        <SiteHeader title={currentPlaylist?.name} />
        <PlaylistContent playlistId={playlistId} />
      </SidebarInset>
    </SidebarProvider>
  );
}
