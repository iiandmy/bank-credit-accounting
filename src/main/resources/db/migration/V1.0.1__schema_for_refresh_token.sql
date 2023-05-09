CREATE TABLE IF NOT EXISTS ba_refresh_token
(
    id SERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    user_id SERIAL REFERENCES ba_user(id)
);