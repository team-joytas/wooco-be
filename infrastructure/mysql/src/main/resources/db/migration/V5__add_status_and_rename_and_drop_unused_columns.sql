-- V5__add_status_and_rename_and_drop_unused_columns.sql

-- courses 테이블 수정 사항
ALTER TABLE courses
    DROP COLUMN view_count;

ALTER TABLE courses
    ADD COLUMN course_status VARCHAR(255) DEFAULT 'ACTIVE';

ALTER TABLE courses
    CHANGE COLUMN interest_count like_count BIGINT;

-- like_courses 테이블 수정사항
RENAME TABLE interest_courses TO course_likes;

ALTER TABLE course_likes
    CHANGE COLUMN interest_user_id like_user_id BIGINT;

ALTER TABLE course_likes
    CHANGE COLUMN interest_course_id course_like_id BIGINT;

ALTER TABLE course_likes
    ADD COLUMN like_status VARCHAR(255) DEFAULT 'ACTIVE';

-- course_comments 테이블 수정사항
ALTER TABLE course_comments
    ADD COLUMN comment_status VARCHAR(255) DEFAULT 'ACTIVE';
