import { serve } from "https://deno.land/std/http/server.ts";

serve(async () => {
  return new Response(JSON.stringify({ link_token: "mock-link-token" }), { headers: { "Content-Type": "application/json" } });
});
