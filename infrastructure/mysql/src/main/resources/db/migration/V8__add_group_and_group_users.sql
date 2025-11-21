-- V8__add_group_and_group_users.sql

CREATE TABLE `groups`
(
    group_id     BIGINT PRIMARY KEY,
    owner_id     BIGINT      NOT NULL,
    name         VARCHAR(50) NOT NULL,
    invite_code        VARCHAR(50) NOT NULL,
    group_status VARCHAR(20) NOT NULL,
    created_at   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE group_users
(
    group_user_id BIGINT PRIMARY KEY,
    group_id      BIGINT      NOT NULL,
    user_id       BIGINT      NOT NULL,
    role          VARCHAR(20) NOT NULL,
    status        VARCHAR(20) NOT NULL,
    joined_at     DATE   NOT NULL,
    created_at    TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
