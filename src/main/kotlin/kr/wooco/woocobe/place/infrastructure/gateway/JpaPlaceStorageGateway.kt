package kr.wooco.woocobe.place.infrastructure.gateway

import kr.wooco.woocobe.place.domain.gateway.PlaceStorageGateway
import kr.wooco.woocobe.place.domain.model.Place
import kr.wooco.woocobe.place.infrastructure.storage.PlaceEntity
import kr.wooco.woocobe.place.infrastructure.storage.PlaceJpaRepository
import kr.wooco.woocobe.user.infrastructure.storage.UserJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class JpaPlaceStorageGateway(
    private val userJpaRepository: UserJpaRepository,
    private val placeJpaRepository: PlaceJpaRepository,
) : PlaceStorageGateway {
    override fun save(place: Place): Place {
        placeJpaRepository.save(PlaceEntity.from(place))

        return place
    }

    override fun getByPlaceId(placeId: Long): Place? =
        placeJpaRepository.findByIdOrNull(placeId)?.let { placeEntity ->
            placeEntity.toDomain(
                user = userJpaRepository.findByIdOrNull(placeEntity.userId)!!.toDomain(),
            )
        }

    override fun existsByKakaoMapPlaceId(placeId: String): Boolean = placeJpaRepository.existsByKakaoMapPlaceId(placeId)
}
