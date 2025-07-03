-- V6__add_place_review_status.sql

-- PlaceReview 테이블 수정사항
ALTER TABLE place_reviews
    ADD COLUMN place_review_status VARCHAR(255) DEFAULT 'ACTIVE';
