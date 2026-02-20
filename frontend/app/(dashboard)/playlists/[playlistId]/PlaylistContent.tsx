"use client";

import { DataTable } from "@/components/data-table";
import { useQueryClient } from "@tanstack/react-query";
import { useRouter } from "next/navigation";
import {
  getGetPlaylistQueryKey,
  useDeleteSong,
  useGetPlaylist,
} from "@/api/generated/playlist-management/playlist-management";
import {
  getGetUserPlaylistsQueryKey,
  useLeavePlaylist,
} from "@/api/generated/user-management/user-management";

interface PlaylistContentProps {
  playlistId: number;
}

export default function PlaylistContent({ playlistId }: PlaylistContentProps) {
  const queryClient = useQueryClient();
  const router = useRouter();

  const { data: playlist, isLoading } = useGetPlaylist(playlistId);
  const songs = playlist?.songs;

  const { mutate: removeSong } = useDeleteSong();
  const { mutate: leavePlaylist } = useLeavePlaylist();

  const handleDeleteSong = (songId: number): void => {
    removeSong(
      { playlistId, songId },
      {
        onSuccess: () => {
          queryClient.invalidateQueries({ queryKey: getGetPlaylistQueryKey() });
        },
      },
    );
  };

  const handleLeavePlaylist = () => {
    leavePlaylist(
      { playlistId },
      {
        onSuccess: () => {
          queryClient.invalidateQueries({
            queryKey: getGetUserPlaylistsQueryKey(),
          });
          router.push("/playlists");
        },
      },
    );
  };

  return (
    <div className="flex flex-1 flex-col">
      <div className="@container/main flex flex-1 flex-col gap-2">
        <div className="flex flex-col gap-4 py-4 md:gap-6 md:py-6">
          {playlist && (
            <DataTable
              data={songs ?? []}
              playlistId={playlistId}
              inviteCode={playlist.inviteCode}
              onDelete={handleDeleteSong}
              onLeave={handleLeavePlaylist}
              isLoading={isLoading}
            />
          )}
        </div>
      </div>
    </div>
  );
}
