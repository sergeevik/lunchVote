DELETE FROM vote;
DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM lunch;
DELETE FROM restaurant;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('admin', 'admin@lunch.com', '12345'), --100000
  ('Jura', 'jura@lunch.com', 'qwerty'), --100001
  ('user', 'user@lunch.com', 'pass'); --100002

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_USER', 100001),
  ('ROLE_USER', 100002),
  ('ROLE_ADMIN', 100002);

INSERT INTO restaurant (name, address) VALUES
  ('Burger King', 'Square'), --100003
  ('McDonald', 'Square'), --100004
  ('KFC', 'Raduga'); --100005

INSERT INTO lunch (description, price, restaurant_id) VALUES
  ('Voper', 12000, 100003),
  ('Big mack', 8000, 100004),
  ('Chiken', 10000, 100005);

INSERT INTO vote (user_id, restaurant_id) VALUES
  (100000, 100003),
  (100001, 100003),
  (100002, 100005);
