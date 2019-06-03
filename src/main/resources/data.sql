INSERT INTO USERS (LOGIN, EMAIL, FIRST_NAME, LAST_NAME, PASSWORD, ENABLED, REGISTERED)
 VALUES ('user', 'user@gmail.com', 'User_First', 'User_Last', '{noop}password', true, '2012-07-24');
-- VALUES  ('admin', 'admin@javaops.ru', 'Admin_First', 'Admin_Last', '{noop}admin', true, '2012-07-24');

INSERT INTO user_roles (ROLE, USER_ID)
VALUES ('ROLE_USER', 1);--,
--        ('ROLE_ADMIN', 2),
--        ('ROLE_USER', 2);