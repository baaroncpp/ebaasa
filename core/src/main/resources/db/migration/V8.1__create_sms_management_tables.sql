CREATE TABLE t_sms_account(
    id SERIAL primary key,
    created_on timestamp not null default now(),
	modified_on timestamp,
	created_by integer references t_user(id),
	modified_by integer references t_user(id),
	sms_account_type varchar(50) not null,
	closed_on timestamp,
	closed_by integer references t_user(id),
	is_closed boolean not null default false,
	activated_by integer references t_user(id),
	activated_on timestamp,
	is_active boolean not null default false,
	account_sms_count integer,
	is_assigned boolean not null default false,
	assigned_by integer references t_user(id)
);

CREATE TABLE t_sms_package(
    id SERIAL primary key,
    created_on timestamp not null default now(),
	modified_on timestamp,
	created_by integer references t_user(id),
	modified_by integer references t_user(id),
	package_sms_count integer,
	sms_account_id integer references t_sms_account(id),
	sms_package_type varchar(50),
	is_used_up boolean not null default false,
	is_active boolean not null default false,
	used_up_on timestamp,
	activated_on timestamp
);

CREATE TABLE t_sms(
    id SERIAL primary key,
    created_on timestamp not null default now(),
	modified_on timestamp,
	title varchar(50) not null,
	message text,
	source_number varchar(50),
	destination_number varchar(12) not null,
	is_delivered boolean default false,
	delivered_on timestamp,
	sms_package_id SERIAL references t_sms_package(id)
);

CREATE TABLE t_sms_package_type(
    id SERIAL primary key,
    created_on timestamp not null default now(),
	modified_on timestamp,
	created_by integer references t_user(id),
	modified_by integer references t_user(id),
	name varchar(50) not null,
	note text not null,
	sms_count integer not null,
	is_active boolean not null default false,
	activated_by integer references t_user(id),
	closed_by integer references t_user(id),
	is_closed boolean not null default false
);