package kr.wooco.woocobe.place.infrastructure.storage

import kr.wooco.woocobe.place.domain.model.Place
import kr.wooco.woocobe.place.infrastructure.storage.entity.PlaceJpaEntity
import org.springframework.stereotype.Component

@Component
class PlaceStorageMapper {
    fun toDomain(placeJpaEntity: PlaceJpaEntity): Place =
        Place(
            id = placeJpaEntity.id,
            name = placeJpaEntity.name,
            latitude = placeJpaEntity.latitude,
            longitude = placeJpaEntity.longitude,
            address = placeJpaEntity.address,
            kakaoMapPlaceId = placeJpaEntity.kakaoMapPlaceId,
            averageRating = placeJpaEntity.averageRating,
            reviewCount = placeJpaEntity.reviewCount,
            mainImageUrl = placeJpaEntity.mainImageUrl,
        )

    fun toEntity(place: Place): PlaceJpaEntity =
        PlaceJpaEntity(
            id = place.id,
            name = place.name,
            latitude = place.latitude,
            longitude = place.longitude,
            address = place.address,
            kakaoMapPlaceId = place.kakaoMapPlaceId,
            averageRating = place.averageRating,
            reviewCount = place.reviewCount,
            mainImageUrl = place.mainImageUrl,
        )
}
