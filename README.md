# library-management-java

## MySQL Workbench
## DB Schema: librarymanagement
#### db test data
insert into users (username, password) values ('admin', '$2a$12$DXLsYbRce/inTJnlADtySOxvkfa/9huFl4w8MeweRo9y.q2brqqAy'); </br>
insert into users (username, password) values ('user', '$2a$12$fGFGK5hCNqu6boB4V7PkMu8VQPdnvmTSpz.DJgOoUhxfxCe3qcdPK'); </br>
insert into roles (name) values ('ROLE_ADMIN'), ('ROLE_USER'); </br>
insert into users_roles (user_id, role_id) values (1, 1), (2, 2); 
