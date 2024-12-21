package kr.wooco.woocobe.place.infrastructure.storage

import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.common.storage.BaseTimeEntity
import kr.wooco.woocobe.place.domain.model.Place
import kr.wooco.woocobe.user.domain.model.User

@Entity
@Table(name = "places")
class PlaceEntity(
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
    @Column(name = "user_id", nullable = false)
    val userId: Long,
    @Id @Tsid
    @Column(name = "place_id", nullable = false)
    val id: Long? = 0L,
) : BaseTimeEntity() {
    fun toDomain(user: User): Place =
        Place(
            id = id!!,
            user = user,
            name = name,
            latitude = latitude,
            longitude = longitude,
            address = address,
            kakaoMapPlaceId = kakaoMapPlaceId,
            averageRating = averageRating,
            reviewCount = reviewCount,
        )

    companion object {
        fun from(place: Place): PlaceEntity =
            with(place) {
                PlaceEntity(
                    id = id,
                    userId = user.id,
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
