import { serve } from "https://deno.land/std/http/server.ts";

serve(async () => {
  return new Response(JSON.stringify({ accounts: [] }), { headers: { "Content-Type": "application/json" } });
});
