create table t_user_group(
    id BIGSERIAL PRIMARY KEY,
    user_group_name VARCHAR(20) UNIQUE,
    group_note TEXT,
    created_on TIMESTAMP NOT NULL DEFAULT now(),
    modified_on TIMESTAMP
);

create table t_user(
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(20) UNIQUE,
    password TEXT,
    account_locked BOOLEAN NOT NULL DEFAULT TRUE,
    account_expired BOOLEAN NOT NULL DEFAULT TRUE,
    cred_expired BOOLEAN NOT NULL DEFAULT TRUE,
    user_group_id BIGINT NOT NULL REFERENCES t_user_group(id),
    approved BOOLEAN NOT NULL DEFAULT TRUE,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    approved_by INTEGER REFERENCES t_user(id),
    user_type VARCHAR(20) NOT NULL,
    initial_password_reset BOOLEAN NOT NULL DEFAULT FALSE,
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
    role_id BIGINT NOT NULL REFERENCES t_role(id),
    permission_name VARCHAR(20) UNIQUE,
    created_on TIMESTAMP NOT NULL DEFAULT now(),
    modified_on TIMESTAMP
);

create table t_user_approval(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES t_user(id),
    status INTEGER NOT NULL,
    created_on TIMESTAMP NOT NULL DEFAULT now(),
    modified_on TIMESTAMP,
    created_by BIGINT NOT NULL REFERENCES t_user(id),
    modified_by BIGINT REFERENCES t_user(id)
);

create table t_group_authority(
    id BIGSERIAL PRIMARY KEY,
    user_group_id BIGINT NOT NULL REFERENCES t_user_group(id),
    permission_id BIGINT NOT NULL REFERENCES t_permission(id),
    created_on TIMESTAMP NOT NULL DEFAULT now(),
    modified_on TIMESTAMP
);