CREATE TABLE public."user" (
                               id             UUID PRIMARY KEY,
                               name           VARCHAR(255) NOT NULL UNIQUE,
                               email          VARCHAR(255) NOT NULL,
                               password_hash  VARCHAR(255) NOT NULL,
                               role           VARCHAR(50)  NOT NULL,
                               created_at     TIMESTAMP
);