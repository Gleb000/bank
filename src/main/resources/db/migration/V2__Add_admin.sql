insert into usr (id, username, password, money, active)
    values (0, 'admin','123', 100, true);

insert into user_role (user_id, roles)
    values (0, 'USER'), (0, 'ADMIN');