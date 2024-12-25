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

    override fun getByPlaceId(placeId: Long): Place? = placeJpaRepository.findByIdOrNull(placeId)?.toDomain()

    override fun existsByKakaoMapPlaceId(kakaoMapPlaceId: String): Boolean = placeJpaRepository.existsByKakaoMapPlaceId(kakaoMapPlaceId)

    override fun getByKakaoMapPlaceId(kakaoMapPlaceId: String): Place = placeJpaRepository.findByKakaoMapPlaceId(kakaoMapPlaceId).toDomain()
}
