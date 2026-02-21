"use client";

import {
  getGetUserPlaylistsQueryKey,
  useJoinPlaylist,
} from "@/api/generated/user-management/user-management";
import { useQueryClient } from "@tanstack/react-query";
import { useRouter } from "next/navigation";
import React, { use } from "react";

interface PageProps {
  params: Promise<{ inviteCode: string }>;
}

export default function JoinPlaylistPage({ params }: PageProps) {
  const { inviteCode } = use(params);
  const router = useRouter();
  const queryClient = useQueryClient();
  const { mutate: joinPlaylist } = useJoinPlaylist();

  React.useEffect(() => {
    joinPlaylist(
      {
        playlistInviteCode: inviteCode,
      },
      {
        onSuccess: (playlist) => {
          (queryClient.invalidateQueries({
            queryKey: getGetUserPlaylistsQueryKey(),
          }),
            router.push(`/playlists/${playlist.id}`));
        },
        onError: () => {
          router.push("/playlists?error=invalid_invite");
        },
      },
    );
  }, []);

  return (
    <div className="flex items-center justify-center min-h-screen">
      <p className="text-muted-foreground">Joining playlist...</p>
    </div>
  );
}
