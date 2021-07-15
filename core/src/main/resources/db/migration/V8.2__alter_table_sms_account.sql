ALTER TABLE core.t_sms_account ADD COLUMN sms_account_name varchar(50) unique not null;
ALTER TABLE core.t_sms_account ADD COLUMN suspended_by integer references t_user(id);
ALTER TABLE core.t_sms_account ADD COLUMN suspended_on timestamp;
ALTER TABLE core.t_sms_account ADD COLUMN is_suspended boolean not null default false;
