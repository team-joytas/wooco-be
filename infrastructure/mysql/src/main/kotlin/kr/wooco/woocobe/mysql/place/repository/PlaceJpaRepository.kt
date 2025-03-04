package kr.wooco.woocobe.mysql.place.repository

import kr.wooco.woocobe.mysql.place.entity.PlaceJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PlaceJpaRepository : JpaRepository<PlaceJpaEntity, Long> {
    fun findByKakaoPlaceId(kakaoMapPlaceId: String): PlaceJpaEntity?

    fun findAllByIdIn(placeIds: List<Long>): List<PlaceJpaEntity>
}
