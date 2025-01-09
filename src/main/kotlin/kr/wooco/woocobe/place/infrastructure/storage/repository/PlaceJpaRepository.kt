package kr.wooco.woocobe.place.infrastructure.storage.repository

import kr.wooco.woocobe.place.infrastructure.storage.entity.PlaceJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PlaceJpaRepository : JpaRepository<PlaceJpaEntity, Long> {
    fun findByKakaoMapPlaceId(kakaoMapPlaceId: String): PlaceJpaEntity?

    fun findAllByIdIn(placeIds: List<Long>): List<PlaceJpaEntity>
}
