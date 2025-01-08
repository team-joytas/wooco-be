package kr.wooco.woocobe.place.infrastructure.gateway

import kr.wooco.woocobe.place.domain.gateway.PlaceStorageGateway
import kr.wooco.woocobe.place.domain.model.Place
import kr.wooco.woocobe.place.infrastructure.storage.PlaceEntity
import kr.wooco.woocobe.place.infrastructure.storage.PlaceJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class JpaPlaceStorageGateway(
    private val placeJpaRepository: PlaceJpaRepository,
) : PlaceStorageGateway {
    override fun save(place: Place): Place {
        placeJpaRepository.save(PlaceEntity.from(place))
        return place
    }

    override fun getByPlaceId(placeId: Long): Place {
        val placeEntity = placeJpaRepository.findByIdOrNull(placeId)
            ?: throw RuntimeException()
        return placeEntity.toDomain()
    }

    override fun getByKakaoMapPlaceId(kakaoMapPlaceId: String): Place? =
        placeJpaRepository.findByKakaoMapPlaceId(kakaoMapPlaceId)?.toDomain()

    override fun getAllByPlaceIds(placeIds: List<Long>): List<Place> = placeJpaRepository.findAllByIdIn(placeIds).map { it.toDomain() }
}
