ALTER TABLE course_meta
    ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

UPDATE course_meta cm
    JOIN courses c ON c.course_id = cm.course_id
SET cm.created_at = c.created_at;
