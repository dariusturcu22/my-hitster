import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  async rewrites() {
    return [
      {
        source: "/backend/:path*",
        destination: "https://backend-weathered-grass-688.fly.dev/:path*",
      },
    ];
  },
};

export default nextConfig;
