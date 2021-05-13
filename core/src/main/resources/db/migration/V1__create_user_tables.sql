create table t_user(
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(20) UNIQUE,
    password TEXT,
    account_locked BOOLEAN NOT NULL DEFAULT FALSE,
    account_expired BOOLEAN NOT NULL DEFAULT FALSE,
    cred_expired BOOLEAN NOT NULL DEFAULT FALSE,
    user_group_id SERIAL NOT NULL REFERENCES t_user_group(id),
    approved BOOLEAN NOT NULL DEFAULT FALSE,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    approved_by SERIAL REFERENCES t_user(id),
    initial_password_reset BOOLEAN NOT NULL DEFAULT FALSE,
    created_on TIMESTAMP NOT NULL DEFAULT now(),
    modified_on TIMESTAMP
);

create table t_user_group(
    id BIGSERIAL PRIMARY KEY,
    user_group_name VARCHAR(20) UNIQUE,
    group_note TEXT,
    created_on TIMESTAMP NOT NULL DEFAULT now(),
    modified_on TIMESTAMP
);

create table t_role(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(20) UNIQUE,
    note TEXT,
    created_on TIMESTAMP NOT NULL DEFAULT now(),
    modified_on TIMESTAMP
);

create table t_permission(
    id BIGSERIAL PRIMARY KEY,
    role_id SERIAL REFERENCES t_role(id),
    permission_name VARCHAR(20) UNIQUE,
    created_on TIMESTAMP NOT NULL DEFAULT now(),
    modified_on TIMESTAMP
);

create table t_user_approval(
    id BIGSERIAL PRIMARY KEY,
    user_id SERIAL REFERENCES t_user(id),
    status INTEGER NOT NULL,
    created_on TIMESTAMP NOT NULL DEFAULT now(),
    modified_on TIMESTAMP,
    created_by SERIAL NOT NULL REFERENCES t_user(id),
    modified_by SERIAL REFERENCES t_user(id)
);

create table t_group_authority(
    id BIGSERIAL PRIMARY KEY,
    user_group_id SERIAL NOT NULL REFERENCES t_user_group(id),
    permission_id SERIAL NOT NULL REFERENCES t_permission(id),
    created_on TIMESTAMP NOT NULL DEFAULT now(),
    modified_on TIMESTAMP,
);