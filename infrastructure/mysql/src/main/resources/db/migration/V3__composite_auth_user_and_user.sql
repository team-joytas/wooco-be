-- V3__composite_auth_user_and_user.sql

-- 1. user 테이블에 기존 auth_users 컬럼 추가
ALTER TABLE users
    ADD COLUMN social_id   VARCHAR(16),
    ADD COLUMN social_type VARCHAR(16);

-- 2. auth user 테이블 삭제
DROP TABLE auth_users;

