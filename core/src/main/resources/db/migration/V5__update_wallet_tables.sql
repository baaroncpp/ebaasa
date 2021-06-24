ALTER TABLE core.t_cash_flow ADD COLUMN is_rejected boolean not null default false;
ALTER TABLE core.t_wallet_account ADD COLUMN date_to_notify_at timestamp;