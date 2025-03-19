-- V2__update_columns_notifications_and_plans.sql

ALTER TABLE notifications
    ADD COLUMN read_status varchar(20) NOT NULL DEFAULT 'UNREAD';

ALTER TABLE notifications
    DROP COLUMN is_read;

ALTER TABLE device_tokens
    DROP COLUMN is_active;

ALTER TABLE plans
    DROP COLUMN is_shared;
