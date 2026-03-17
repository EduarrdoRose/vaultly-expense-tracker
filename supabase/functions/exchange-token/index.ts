import { serve } from "https://deno.land/std/http/server.ts";

serve(async (req) => {
  const { public_token } = await req.json();
  if (!public_token) return new Response("Missing public_token", { status: 400 });
  return new Response(JSON.stringify({ ok: true }), { headers: { "Content-Type": "application/json" } });
});
