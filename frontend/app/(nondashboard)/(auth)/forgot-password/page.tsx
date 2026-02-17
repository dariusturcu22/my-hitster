import { Button } from "@/components/shadcn/button";
import { Input } from "@/components/shadcn/input";
import { Label } from "@/components/shadcn/label";
import { IconArrowLeft } from "@tabler/icons-react";
import Link from "next/link";

export default function ForgotPasswordPage() {
  return (
    <section className="flex px-4 py-8">
      <form className="max-w-92 m-auto h-fit w-full">
        <div className="p-6">
          <div>
            <h1 className="mb-1 mt-4 text-xl font-semibold">
              Reset your password
            </h1>
            <p className="text-muted-foreground text-sm">
              Enter your email and we'll send you a link to reset your password.
            </p>
          </div>

          <div className="mt-6 space-y-5">
            <div className="space-y-2">
              <Label htmlFor="email" className="text-sm">
                Email
              </Label>
              <Input
                type="email"
                required
                name="email"
                id="email"
                placeholder="you@example.com"
              />
            </div>

            <Button className="w-full">Send Reset Link</Button>
          </div>
        </div>

        <p className="text-muted-foreground text-center text-sm">
          <Button asChild variant="link" className="px-2 gap-1">
            <Link href="/login">
              <IconArrowLeft className="size-3" />
              Back to sign in
            </Link>
          </Button>
        </p>
      </form>
    </section>
  );
}
