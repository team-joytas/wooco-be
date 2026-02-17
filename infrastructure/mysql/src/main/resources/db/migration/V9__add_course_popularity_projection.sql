CREATE TABLE course_meta (
    course_id BIGINT PRIMARY KEY,
    comment_count BIGINT NOT NULL DEFAULT 0,
    like_count BIGINT NOT NULL DEFAULT 0,
    popularity_score DOUBLE NOT NULL DEFAULT 0
);

INSERT INTO course_meta (course_id, comment_count, like_count, popularity_score)
SELECT
    course_id,
    comment_count,
    like_count,
    LOG10(like_count + 1) + UNIX_TIMESTAMP(created_at) / 45000
FROM courses;

ALTER TABLE courses
    DROP COLUMN comment_count,
    DROP COLUMN like_count;
