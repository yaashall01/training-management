CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       user_id UUID NOT NULL UNIQUE,
                       first_name VARCHAR(255) NOT NULL,
                       last_name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       phone VARCHAR(255) UNIQUE,
                       user_name VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       gender VARCHAR(255),
                       address TEXT,
                       dob DATE,
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       update_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       user_role VARCHAR(255),
                       profile_image BYTEA
);