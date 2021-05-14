
insert into t_user_group(user_group_name, group_note) values('ADMIN_GROUP', 'system administrative users');

insert into t_role(name, note) values('ADMIN_ROLE', 'administarative role');

insert into t_permission(role_name, permission_name)
values
('ADMIN_ROLE', 'ADMIN_ROLE.WRITE'),
('ADMIN_ROLE', 'ADMIN_ROLE.READ'),
('ADMIN_ROLE', 'ADMIN_ROLE.UPDATE'),
('ADMIN_ROLE', 'ADMIN_ROLE.DELETE');

insert into t_group_authority(user_group_name, permission_name)
values
('ADMIN_GROUP', 'ADMIN_ROLE.WRITE'),
('ADMIN_GROUP', 'ADMIN_ROLE.READ'),
('ADMIN_GROUP', 'ADMIN_ROLE.UPDATE'),
('ADMIN_GROUP', 'ADMIN_ROLE.DELETE');


insert into t_user(username, password, user_group_name, user_type)
values('admin', '$2y$12$W2MKBBqZgWaBFfa/8AhqHua.g8IcflAsugtFPPxN13x0jQOiCm9gO', 'ADMIN_GROUP', 'ADMIN');