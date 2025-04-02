-- V4__add_status_notifications_and_plans.sql

ALTER TABLE notifications
    ADD COLUMN status varchar(20) NOT NULL DEFAULT 'ACTIVE';

ALTER TABLE device_tokens
    ADD COLUMN status varchar(20) NOT NULL DEFAULT 'ACTIVE';

ALTER TABLE plans
    ADD COLUMN status varchar(20) NOT NULL DEFAULT 'ACTIVE';
