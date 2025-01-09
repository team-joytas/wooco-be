package kr.wooco.woocobe.place.infrastructure.storage.entity

import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.common.infrastructure.storage.BaseTimeEntity
import kr.wooco.woocobe.place.domain.model.Place

@Entity
@Table(name = "places")
class PlaceJpaEntity(
    @Column(name = "review_count", nullable = false)
    val reviewCount: Long,
    @Column(name = "average_rating", nullable = false)
    val averageRating: Double,
    @Column(name = "kakao_map_place_id", nullable = false)
    val kakaoMapPlaceId: String,
    @Column(name = "longitude", nullable = false)
    val longitude: Double,
    @Column(name = "latitude", nullable = false)
    val latitude: Double,
    @Column(name = "address", nullable = false)
    val address: String,
    @Column(name = "name", nullable = false)
    val name: String,
    @Id @Tsid
    @Column(name = "place_id", nullable = false)
    val id: Long? = 0L,
) : BaseTimeEntity() {
    fun toDomain(): Place =
        Place(
            id = id!!,
            name = name,
            latitude = latitude,
            longitude = longitude,
            address = address,
            kakaoMapPlaceId = kakaoMapPlaceId,
            averageRating = averageRating,
            reviewCount = reviewCount,
        )

    companion object {
        fun from(place: Place): PlaceJpaEntity =
            with(place) {
                PlaceJpaEntity(
                    id = id,
                    name = name,
                    latitude = latitude,
                    longitude = longitude,
                    address = address,
                    kakaoMapPlaceId = kakaoMapPlaceId,
                    averageRating = averageRating,
                    reviewCount = reviewCount,
                )
            }
    }
}
