import { AudioWaveform } from "lucide-react";

export const LogoIcon = () => {
  return (
    <div className="flex items-center gap-2">
      <div className="bg-primary rounded-md p-1.5">
        <AudioWaveform className="size-4 text-primary-foreground" />
      </div>
      <span className="font-semibold text-sm">My Hitster</span>
    </div>
  );
};
