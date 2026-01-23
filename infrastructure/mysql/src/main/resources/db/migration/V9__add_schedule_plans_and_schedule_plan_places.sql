-- V9__add_schedule_plans_and_schedule_plan_places.sql

-- TABLE: schedule_plans 생성
CREATE TABLE schedule_plans
(
    schedule_plan_id     BIGINT       NOT NULL PRIMARY KEY,
    schedule_plan_status VARCHAR(50)  NOT NULL,
    visit_date           DATE         NOT NULL,
    title                VARCHAR(255) NOT NULL,
    group_id             BIGINT       NOT NULL,

    created_at           TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at           TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- TABLE: schedule_plan_places 생성
CREATE TABLE schedule_plan_places
(
    schedule_plan_place_id    BIGINT    NOT NULL PRIMARY KEY,
    schedule_plan_place_order INT       NOT NULL,
    schedule_plan_id          BIGINT    NOT NULL,
    place_id                  BIGINT    NOT NULL,

    created_at                TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
