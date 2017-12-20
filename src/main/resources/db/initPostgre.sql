DROP TABLE IF EXISTS lunch;
DROP TABLE IF EXISTS restaurant;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS voite;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 100000;

CREATE TABLE users
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name             VARCHAR                 NOT NULL,
  email            VARCHAR                 NOT NULL,
  password         VARCHAR                 NOT NULL,
  registered       TIMESTAMP DEFAULT now() NOT NULL,
  enabled          BOOL DEFAULT TRUE       NOT NULL
);

CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR,
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurant
(
  id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name          VARCHAR  NOT NULL,
  address       TEXT     NOT NULL,
  CONSTRAINT restaurant_idx UNIQUE ( name, address)
);

CREATE TABLE lunch
(
  id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  date          TIMESTAMP DEFAULT now() NOT NULL,
  description   TEXT                    NOT NULL,
  price         INTEGER                 NOT NULL,
  restaurant_id INTEGER                 NOT NULL,
  CONSTRAINT lunch_idx UNIQUE ( description, restaurant_id),
  FOREIGN KEY (restaurant_id) REFERENCES restaurant(id) ON DELETE CASCADE
);


CREATE TABLE vote
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  user_id          INTEGER    NOT NULL,
  restaurant_id    INTEGER    NOT NULL,
  date             TIMESTAMP DEFAULT now() NOT NULL,

  CONSTRAINT vote_idx UNIQUE (user_id, restaurant_id, date),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (restaurant_id) REFERENCES restaurant(id) ON DELETE CASCADE
);