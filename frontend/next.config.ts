import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  async rewrites() {
    return [
      {
        source: "/backend/:path*",
        destination: "https://api.dariusturcu22.com/:path*",
      },
    ];
  },
};

export default nextConfig;
