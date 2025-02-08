package kr.wooco.woocobe.place.adapter.out.presistence.repository

import kr.wooco.woocobe.place.adapter.out.presistence.entity.PlaceJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PlaceJpaRepository : JpaRepository<PlaceJpaEntity, Long> {
    fun findByKakaoPlaceId(kakaoMapPlaceId: String): PlaceJpaEntity?

    fun findAllByIdIn(placeIds: List<Long>): List<PlaceJpaEntity>
}
