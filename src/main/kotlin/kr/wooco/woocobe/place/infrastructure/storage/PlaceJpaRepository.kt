package kr.wooco.woocobe.place.infrastructure.storage

import org.springframework.data.jpa.repository.JpaRepository

interface PlaceJpaRepository : JpaRepository<PlaceEntity, Long> {
    fun existsByKakaoMapPlaceId(kakaoMapPlaceId: String): Boolean

    fun findByKakaoMapPlaceId(kakaoMapPlaceId: String): PlaceEntity

    fun findAllByIdIn(placeIds: List<Long>): List<PlaceEntity>
}
