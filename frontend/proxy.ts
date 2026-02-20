import { NextRequest, NextResponse } from "next/server";

const PUBLIC_ROUTES = ["/login", "/register", "/oauth2/redirect", "/landing"];

export default function proxy(request: NextRequest) {
  const { pathname } = request.nextUrl;

  const isPublic = PUBLIC_ROUTES.some((route) => pathname.startsWith(route));
  const hasToken = request.cookies.has("access_token");

  if (!isPublic && !hasToken) {
    return NextResponse.redirect(new URL("/login", request.url));
  }

  if (isPublic && hasToken && pathname !== "/oauth2/redirect") {
    return NextResponse.redirect(new URL("/playlists", request.url));
  }

  return NextResponse.next();
}

export const config = {
  matcher: ["/((?!_next/static|_next/image|favicon.ico|.*\\.png$).*)"],
};
