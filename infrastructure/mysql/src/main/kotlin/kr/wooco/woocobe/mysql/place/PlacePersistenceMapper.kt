package kr.wooco.woocobe.mysql.place

import kr.wooco.woocobe.core.place.domain.entity.Place
import kr.wooco.woocobe.mysql.place.entity.PlaceJpaEntity
import org.springframework.stereotype.Component

@Component
internal class PlacePersistenceMapper {
    fun toDomain(placeJpaEntity: PlaceJpaEntity): Place =
        Place(
            id = placeJpaEntity.id,
            name = placeJpaEntity.name,
            latitude = placeJpaEntity.latitude,
            longitude = placeJpaEntity.longitude,
            address = placeJpaEntity.address,
            kakaoPlaceId = placeJpaEntity.kakaoPlaceId,
            averageRating = placeJpaEntity.averageRating,
            reviewCount = placeJpaEntity.reviewCount,
            thumbnailUrl = placeJpaEntity.thumbnailUrl,
            phoneNumber = placeJpaEntity.phoneNumber,
        )

    fun toEntity(place: Place): PlaceJpaEntity =
        PlaceJpaEntity(
            id = place.id,
            name = place.name,
            latitude = place.latitude,
            longitude = place.longitude,
            address = place.address,
            kakaoPlaceId = place.kakaoPlaceId,
            averageRating = place.averageRating,
            reviewCount = place.reviewCount,
            thumbnailUrl = place.thumbnailUrl,
            phoneNumber = place.phoneNumber,
        )
}
