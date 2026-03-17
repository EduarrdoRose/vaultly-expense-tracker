# Vaultly Expense Tracker

Production-oriented Android scaffold for offline-first expense tracking with Plaid + Supabase.

## Setup
1. Copy `local.properties.example` to `local.properties`.
2. Fill Supabase and Plaid keys.
3. Run `./gradlew test`.

## Environment Variables
- PLAID_CLIENT_ID
- PLAID_SECRET
- PLAID_ENV
- SUPABASE_URL
- SUPABASE_ANON_KEY
- SUPABASE_SERVICE_ROLE_KEY
- ENCRYPTION_KEY

## Plaid Sandbox
Use institution `ins_116794` (Banco Popular PR) and country code `US`.

## Supabase
Apply SQL migration in `supabase/migrations` and deploy functions in `supabase/functions`.
