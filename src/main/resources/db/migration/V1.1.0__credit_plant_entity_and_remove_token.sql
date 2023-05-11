CREATE TABLE IF NOT EXISTS ba_credit_plan
(
    id SERIAL PRIMARY KEY,
    rate REAL NOT NULL,
    credit_amount REAL NOT NULL,
    first_payment REAL NOT NULL
);

ALTER TABLE ba_credit
    DROP COLUMN rate;

ALTER TABLE ba_credit
    DROP COLUMN credit_amount;

ALTER TABLE ba_credit
    ADD COLUMN plan_id SERIAL REFERENCES ba_credit_plan(id);

DROP TABLE ba_refresh_token;