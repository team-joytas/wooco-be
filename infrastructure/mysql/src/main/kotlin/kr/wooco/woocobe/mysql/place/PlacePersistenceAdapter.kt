package kr.wooco.woocobe.mysql.place

import kr.wooco.woocobe.core.place.application.port.out.PlaceCommandPort
import kr.wooco.woocobe.core.place.application.port.out.PlaceQueryPort
import kr.wooco.woocobe.core.place.domain.entity.Place
import kr.wooco.woocobe.core.place.domain.exception.NotExistsPlaceException
import kr.wooco.woocobe.mysql.place.repository.PlaceJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
internal class PlacePersistenceAdapter(
    private val placeJpaRepository: PlaceJpaRepository,
) : PlaceQueryPort,
    PlaceCommandPort {
    override fun savePlace(place: Place): Place {
        val placeEntity = PlacePersistenceMapper.toJpaEntity(place)
        val savedPlaceEntity = placeJpaRepository.save(placeEntity)
        return PlacePersistenceMapper.toDomainEntity(savedPlaceEntity)
    }

    override fun getByPlaceId(placeId: Long): Place {
        val placeEntity = placeJpaRepository.findByIdOrNull(placeId)
            ?: throw NotExistsPlaceException
        return PlacePersistenceMapper.toDomainEntity(placeEntity)
    }

    override fun getOrNullByKakaoPlaceId(kakaoPlaceId: String): Place? {
        val placeEntity = placeJpaRepository.findByKakaoPlaceId(kakaoPlaceId)
        return placeEntity?.let { PlacePersistenceMapper.toDomainEntity(it) }
    }

    override fun getAllByPlaceIds(placeIds: List<Long>): List<Place> {
        val placeEntities = placeJpaRepository.findAllByIdIn(placeIds)
        return placeEntities.map { PlacePersistenceMapper.toDomainEntity(it) }
    }
}
