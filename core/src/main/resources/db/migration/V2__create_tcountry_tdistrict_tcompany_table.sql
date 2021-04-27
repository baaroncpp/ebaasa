create table t_country (
	id SERIAL primary key,
	name VARCHAR(50) not null,
	iso_alpha_2 CHARACTER(2) unique not null,
	iso_alpha_3 CHARACTER(3) unique not null,
	iso_numeric INTEGER unique not null,
	created_on timestamp not null default now(),
	modified_on timestamp
);

create table t_district (
	id SERIAL primary key,
	country_id SERIAL references t_country(id),
	name VARCHAR(50),
	region VARCHAR(50),
    created_on timestamp not null default now(),
    modified_on timestamp
);

create table t_company(
    id BIGSERIAL primary key,
	business_name VARCHAR(200) not NULL,
	nature_of_business VARCHAR(100),
	physical_address text not NULL,
	phone_number VARCHAR(25) not null,
	district varchar references t_district(id),
	tin_number varchar(50) unique not null,
	registration_country INTEGER references t_country(id),
	contact_person varchar(100),
	contact_identification VARCHAR(20) not null,
	contact_identification_number varchar(50) not null,
	contact_identification_path text,
	contact_phone_number varchar(25),
	email varchar(100),
	note text,
	created_on timestamp not null default now(),
	created_by varchar not null references t_user(id),
	modified_on timestamp,
	modified_by varchar references t_user(id)
);