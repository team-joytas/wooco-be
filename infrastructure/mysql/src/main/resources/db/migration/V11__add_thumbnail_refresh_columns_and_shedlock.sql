-- 장소 썸네일 갱신 스케줄러용 컬럼 추가
ALTER TABLE places ADD COLUMN thumbnail_fetched_at DATETIME DEFAULT NULL;
ALTER TABLE places ADD COLUMN thumbnail_refresh_fail_count INT DEFAULT 0 NOT NULL;

-- 기존 데이터 backfill (thumbnail_url이 있는 장소는 생성일을 기준으로 설정)
UPDATE places SET thumbnail_fetched_at = created_at WHERE thumbnail_url IS NOT NULL AND thumbnail_url != '';

-- ShedLock 테이블 (분산 락용)
CREATE TABLE shedlock (
    name VARCHAR(64) NOT NULL,
    lock_until TIMESTAMP(3) NOT NULL,
    locked_at TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    locked_by VARCHAR(255) NOT NULL,
    PRIMARY KEY (name)
);
