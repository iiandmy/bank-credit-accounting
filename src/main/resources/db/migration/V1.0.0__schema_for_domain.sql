CREATE TABLE IF NOT EXISTS ba_user
(
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    f_name VARCHAR(50) NOT NULL,
    l_name VARCHAR(50) NOT NULL,
    password_hash VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS ba_status
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS ba_credit
(
    id SERIAL PRIMARY KEY,
    user_id SERIAL REFERENCES ba_user(id),
    status_id SERIAL REFERENCES ba_status(id),
    expiring_date DATE NOT NULL,
    start_date DATE NOT NULL,
    rate REAL NOT NULL
);

CREATE TABLE IF NOT EXISTS ba_role
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS ba_user_role
(
    user_id SERIAL REFERENCES ba_user(id),
    role_id SERIAL REFERENCES ba_role(id),
    PRIMARY KEY (user_id, role_id)
)
