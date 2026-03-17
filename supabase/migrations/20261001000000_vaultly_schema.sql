create extension if not exists pgcrypto;

create table if not exists public.profiles (
  id uuid references auth.users primary key,
  full_name text,
  avatar_url text,
  created_at timestamptz default now()
);

create table if not exists public.plaid_items (
  id uuid primary key default gen_random_uuid(),
  user_id uuid references auth.users not null,
  institution_id text not null,
  institution_name text not null,
  access_token_encrypted text not null,
  cursor text,
  status text default 'active',
  created_at timestamptz default now()
);

create table if not exists public.accounts (
  id text primary key,
  user_id uuid references auth.users not null,
  plaid_item_id uuid references public.plaid_items,
  name text not null,
  official_name text,
  type text,
  subtype text,
  current_balance numeric(12,2),
  available_balance numeric(12,2),
  currency_code text default 'USD',
  mask text,
  updated_at timestamptz default now()
);

create table if not exists public.transactions (
  id text primary key,
  user_id uuid references auth.users not null,
  account_id text references public.accounts,
  plaid_category text[],
  custom_category text,
  merchant_name text,
  amount numeric(12,2) not null,
  currency_code text default 'USD',
  date date not null,
  authorized_date date,
  payment_channel text,
  pending boolean default false,
  logo_url text,
  website text,
  note text,
  created_at timestamptz default now()
);

create table if not exists public.budgets (
  id uuid primary key default gen_random_uuid(),
  user_id uuid references auth.users not null,
  category text not null,
  amount numeric(12,2) not null,
  period text default 'monthly',
  created_at timestamptz default now()
);

alter table public.profiles enable row level security;
alter table public.plaid_items enable row level security;
alter table public.accounts enable row level security;
alter table public.transactions enable row level security;
alter table public.budgets enable row level security;

drop policy if exists "users see own profiles" on public.profiles;
drop policy if exists "users see own items" on public.plaid_items;
drop policy if exists "users see own accounts" on public.accounts;
drop policy if exists "users see own transactions" on public.transactions;
drop policy if exists "users see own budgets" on public.budgets;

create policy "users see own profiles" on public.profiles
  for all using (auth.uid() = id) with check (auth.uid() = id);

create policy "users see own items" on public.plaid_items
  for all using (auth.uid() = user_id) with check (auth.uid() = user_id);

create policy "users see own accounts" on public.accounts
  for all using (auth.uid() = user_id) with check (auth.uid() = user_id);

create policy "users see own transactions" on public.transactions
  for all using (auth.uid() = user_id) with check (auth.uid() = user_id);

create policy "users see own budgets" on public.budgets
  for all using (auth.uid() = user_id) with check (auth.uid() = user_id);

create index if not exists idx_transactions_user_date on public.transactions(user_id, date desc);
create index if not exists idx_transactions_category on public.transactions(user_id, custom_category);
create index if not exists idx_accounts_user on public.accounts(user_id);
