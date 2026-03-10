import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  async rewrites() {
    return [
      {
        source: "/backend/:path*",
        destination: "https://api.dariusturcu22.com/:path*",
      },
      {
        source: "/login/oauth2/:path*",
        destination: "https://api.dariusturcu22.com/login/oauth2/:path*",
      },
    ];
  },
};

export default nextConfig;
