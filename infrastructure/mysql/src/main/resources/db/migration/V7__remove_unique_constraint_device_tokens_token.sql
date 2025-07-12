-- V7__remove_unique_constraint_device_tokens_token.sql

-- device_tokens 테이블 수정 사항
ALTER TABLE device_tokens
    DROP INDEX token;
