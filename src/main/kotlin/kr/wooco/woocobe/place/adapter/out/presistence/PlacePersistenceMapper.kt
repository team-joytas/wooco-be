package kr.wooco.woocobe.place.adapter.out.presistence

import kr.wooco.woocobe.place.adapter.out.presistence.entity.PlaceJpaEntity
import kr.wooco.woocobe.place.adapter.out.presistence.entity.PlaceOneLineReviewStatJpaEntity
import kr.wooco.woocobe.place.domain.entity.Place
import kr.wooco.woocobe.place.domain.entity.PlaceOneLineReviewStat
import org.springframework.stereotype.Component

@Component
internal class PlacePersistenceMapper {
    fun toDomain(
        placeJpaEntity: PlaceJpaEntity,
        placeOneLineReviewStatJpaEntities: List<PlaceOneLineReviewStatJpaEntity>,
    ): Place =
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
            placeOneLineReviewStats = placeOneLineReviewStatJpaEntities.map {
                PlaceOneLineReviewStat(
                    contents = it.contents,
                    count = it.count,
                )
            },
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

    fun toEntity(
        placeOneLineReviewStat: PlaceOneLineReviewStat,
        placeId: Long,
    ): PlaceOneLineReviewStatJpaEntity =
        PlaceOneLineReviewStatJpaEntity(
            placeId = placeId,
            contents = placeOneLineReviewStat.contents,
            count = placeOneLineReviewStat.count,
        )
}
