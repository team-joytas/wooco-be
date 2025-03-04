-- V1__init.sql

CREATE TABLE auth_users (
    auth_user_id BIGINT PRIMARY KEY,
    social_type VARCHAR(255) NOT NULL,
    social_id VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL
);

CREATE TABLE course_categories (
    course_category_id BIGINT PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL,
    course_id BIGINT NOT NULL
);

CREATE TABLE courses (
    course_id BIGINT PRIMARY KEY,
    comment_count BIGINT NOT NULL,
    interest_count BIGINT NOT NULL,
    view_count BIGINT NOT NULL,
    visit_date DATE NOT NULL,
    contents TEXT NOT NULL,
    secondary_region VARCHAR(255) NOT NULL,
    primary_region VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE course_places (
    course_place_id BIGINT PRIMARY KEY,
    course_place_order INT NOT NULL,
    course_id BIGINT NOT NULL,
    place_id BIGINT NOT NULL
);

CREATE TABLE interest_courses (
    interest_course_id BIGINT PRIMARY KEY,
    course_id BIGINT NOT NULL,
    interest_user_id BIGINT NOT NULL
);

CREATE TABLE course_comments (
    course_comment_id BIGINT PRIMARY KEY,
    contents TEXT NOT NULL,
    course_id BIGINT NOT NULL,
    comment_user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE device_tokens (
    device_token_id BIGINT PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    is_active BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE notifications (
    notification_id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    target_id BIGINT NOT NULL,
    target_name VARCHAR(255) NOT NULL,
    is_read BOOLEAN NOT NULL,
    type VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE places (
    place_id BIGINT PRIMARY KEY,
    phone_number VARCHAR(255) NOT NULL,
    thumbnail_url VARCHAR(255) NOT NULL,
    review_count BIGINT NOT NULL,
    average_rating DOUBLE NOT NULL,
    kakao_map_place_id VARCHAR(255) NOT NULL,
    longitude DOUBLE NOT NULL,
    latitude DOUBLE NOT NULL,
    address VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE place_one_line_reviews (
    place_one_line_review_id BIGINT PRIMARY KEY,
    contents VARCHAR(255) NOT NULL,
    place_review_id BIGINT NOT NULL,
    place_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE place_review_images (
    place_review_images_id BIGINT PRIMARY KEY,
    image_url VARCHAR(255) NOT NULL,
    place_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE place_reviews (
    place_review_id BIGINT PRIMARY KEY,
    contents TEXT NOT NULL,
    rating DOUBLE NOT NULL,
    user_id BIGINT NOT NULL,
    place_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE plans (
    plan_id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(20) NOT NULL,
    contents TEXT NOT NULL,
    primary_region VARCHAR(255) NOT NULL,
    secondary_region VARCHAR(255) NOT NULL,
    is_shared BOOLEAN NOT NULL,
    visit_date DATE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE plan_places (
    plan_place_id BIGINT PRIMARY KEY,
    plan_place_order INT NOT NULL,
    plan_id BIGINT NOT NULL,
    place_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE preference_regions (
    region_id BIGINT PRIMARY KEY,
    primary_region VARCHAR(255) NOT NULL,
    secondary_region VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL
);

CREATE TABLE users (
    user_id BIGINT PRIMARY KEY,
    status VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    profile_url VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
