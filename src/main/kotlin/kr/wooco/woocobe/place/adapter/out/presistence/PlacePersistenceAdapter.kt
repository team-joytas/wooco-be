package kr.wooco.woocobe.place.adapter.out.presistence

import kr.wooco.woocobe.place.adapter.out.presistence.repository.PlaceJpaRepository
import kr.wooco.woocobe.place.application.port.out.LoadPlacePersistencePort
import kr.wooco.woocobe.place.application.port.out.SavePlacePersistencePort
import kr.wooco.woocobe.place.domain.entity.Place
import kr.wooco.woocobe.place.domain.exception.NotExistsPlaceException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
internal class PlacePersistenceAdapter(
    private val placeJpaRepository: PlaceJpaRepository,
    private val placePersistenceMapper: PlacePersistenceMapper,
) : LoadPlacePersistencePort,
    SavePlacePersistencePort {
    override fun savePlace(place: Place): Place {
        val placeEntity = placePersistenceMapper.toEntity(place)
        val savedPlaceEntity = placeJpaRepository.save(placeEntity)
        return placePersistenceMapper.toDomain(savedPlaceEntity)
    }

    override fun getByPlaceId(placeId: Long): Place {
        val placeEntity = placeJpaRepository.findByIdOrNull(placeId)
            ?: throw NotExistsPlaceException
        return placePersistenceMapper.toDomain(placeEntity)
    }

    override fun getOrNullByKakaoMapPlaceId(kakaoMapPlaceId: String): Place? {
        val placeEntity = placeJpaRepository.findByKakaoPlaceId(kakaoMapPlaceId)
        return placeEntity?.let { placePersistenceMapper.toDomain(it) }
    }

    override fun getAllByPlaceIds(placeIds: List<Long>): List<Place> {
        val placeEntities = placeJpaRepository.findAllByIdIn(placeIds)
        return placeEntities.map { placePersistenceMapper.toDomain(it) }
    }
}
