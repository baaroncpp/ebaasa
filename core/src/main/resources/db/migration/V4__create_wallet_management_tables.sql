CREATE TABLE t_wallet_group(
    id SERIAL primary key,
    created_on timestamp not null default now(),
	created_by integer references t_user(id),
	modified_on timestamp,
	modified_by integer references t_user(id),
	name varchar(100) not null,
	note text,
	wallet_account_type varchar(30),
	is_debited boolean not null default true
);

CREATE TABLE t_wallet(
    id SERIAL primary key,
    created_on timestamp not null default now(),
	created_by integer references t_user(id),
	modified_on timestamp,
	modified_by integer references t_user(id),
	is_active boolean not null default false,
	is_suspended boolean not null default true,
	is_closed boolean not null default true,
	name varchar(100) not null,
	code varchar(100) not null,
	grouping integer references t_wallet_group(id),
	balance_to_notify_at numeric,
	balance_notification_sent_at timestamp,
	available_balance numeric,
	status varchar(30),
	status_description text,
	activated_on timestamp,
	activated_by integer references t_user(id),
	suspended_on timestamp,
	suspended_by integer references t_user(id),
	closed_on timestamp,
	closed_by integer references t_user(id),
	is_assigned boolean not null default false
);

CREATE TABLE t_wallet_transaction(
    id varchar(32) primary key,
    wallet_id integer references t_wallet(id),
    transaction_type varchar(30),
    non_reversal boolean not null default false,
    status varchar(20),
    status_description text,
    balance_before numeric,
    balance_after numeric,
    external_transaction_id varchar(50),
    created_on timestamp not null default now(),
    modified_on timestamp
);

CREATE TABLE t_cash_flow(
    id SERIAL primary key,
    created_on timestamp not null default now(),
	created_by integer references t_user(id),
	modified_on timestamp,
	modified_by integer references t_user(id),
	first_approval boolean not null default false,
	second_approval boolean not null default false,
	external_reference varchar(50),
	amount numeric,
	from_wallet_transaction_id varchar(32) references t_wallet_transaction(id),
	to_wallet_transaction_id varchar(32) references t_wallet_transaction(id),
	from_wallet_id integer references t_wallet(id),
	to_wallet_id integer references t_wallet(id),
	approve_user_1 integer references t_user(id),
	approve_user_2 integer references t_user(id),
	note text,
	note1 text,
	note2 text,
	rejected_on timestamp,
	rejected_by integer references t_user(id),
	flow_type varchar(20),
	first_approve_on timestamp,
	second_approve_on timestamp,
	approval_count integer,
	status varchar(20)
);