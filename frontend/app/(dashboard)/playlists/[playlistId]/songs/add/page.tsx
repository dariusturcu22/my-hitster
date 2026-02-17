import { AppSidebar } from "@/components/app-sidebar";
import { SiteHeader } from "@/components/site-header";
import { SidebarInset, SidebarProvider } from "@/components/shadcn/sidebar";
import { Button } from "@/components/shadcn/button";
import { IconArrowLeft } from "@tabler/icons-react";
import Link from "next/link";
import React from "react";
import { AddSongForm } from "./AddSongForm";

import playlistsData from "@/app/data/playlists.json";

export default async function AddSongPage({
  params,
}: {
  params: Promise<{ playlistId: string }>;
}) {
  const { playlistId } = await params;
  const playlistIdNumber = parseInt(playlistId);
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
        <SiteHeader title="Add Song" />
        <div className="flex flex-1 flex-col p-4 md:p-6">
          <div className="mb-6">
            <Button variant="ghost" size="sm" asChild>
              <Link href={backPath}>
                <IconArrowLeft className="size-4 mr-2" />
                Back to Playlist
              </Link>
            </Button>
          </div>
          <AddSongForm backPath={backPath} />
        </div>
      </SidebarInset>
    </SidebarProvider>
  );
}
