package kr.wooco.woocobe.mysql.place

import kr.wooco.woocobe.core.place.application.port.out.PlaceCommandPort
import kr.wooco.woocobe.core.place.application.port.out.PlaceQueryPort
import kr.wooco.woocobe.core.place.domain.entity.Place
import kr.wooco.woocobe.core.place.domain.exception.NotExistsPlaceException
import kr.wooco.woocobe.mysql.place.entity.PlaceJpaEntity
import kr.wooco.woocobe.mysql.place.repository.PlaceJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
internal class PlacePersistenceAdapter(
    private val placeJpaRepository: PlaceJpaRepository,
) : PlaceQueryPort,
    PlaceCommandPort {
    @Transactional
    override fun savePlace(place: Place): Long =
        if (place.id == 0L) {
            createNew(place)
        } else {
            updatePlace(place)
        }

    fun createNew(place: Place): Long {
        val placeJpaEntity = placeJpaRepository.save(PlaceJpaEntity.create(place))
        return placeJpaEntity.id
    }

    fun updatePlace(place: Place): Long {
        val placeJpaEntity = placeJpaRepository.findByIdOrNull(place.id)!!.let {
            placeJpaRepository.save(it.applyUpdate(place))
        }
        return placeJpaEntity.id
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
