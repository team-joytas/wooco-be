package kr.wooco.woocobe.place.adapter.out.presistence

import kr.wooco.woocobe.place.adapter.out.presistence.repository.PlaceJpaRepository
import kr.wooco.woocobe.place.adapter.out.presistence.repository.PlaceOneLineReviewStatJpaRepository
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
    private val placeOneLineReviewStatJpaRepository: PlaceOneLineReviewStatJpaRepository,
) : LoadPlacePersistencePort,
    SavePlacePersistencePort {
    override fun savePlace(place: Place): Place {
        val placeEntity = placePersistenceMapper.toEntity(place)
        val savedPlaceEntity = placeJpaRepository.save(placeEntity)

        val placeOneLineReviewStatEntities = place.placeOneLineReviewStats.map {
            placePersistenceMapper.toEntity(it, savedPlaceEntity.id)
        }
        placeOneLineReviewStatJpaRepository.saveAll(placeOneLineReviewStatEntities)
        return placePersistenceMapper.toDomain(savedPlaceEntity, placeOneLineReviewStatEntities)
    }

    override fun getByPlaceId(placeId: Long): Place {
        val placeEntity = placeJpaRepository.findByIdOrNull(placeId)
            ?: throw NotExistsPlaceException

        val placeOneLineReviewStats = placeOneLineReviewStatJpaRepository.findAllByPlaceId(placeId)
        return placePersistenceMapper.toDomain(placeEntity, placeOneLineReviewStats)
    }

    override fun getOrNullByKakaoMapPlaceId(kakaoMapPlaceId: String): Place? {
        val placeEntity = placeJpaRepository.findByKakaoPlaceId(kakaoMapPlaceId)
        return placeEntity?.let {
            val placeOneLineReviewStats = placeOneLineReviewStatJpaRepository.findAllByPlaceId(it.id)
            placePersistenceMapper.toDomain(it, placeOneLineReviewStats)
        }
    }

    override fun getAllByPlaceIds(placeIds: List<Long>): List<Place> {
        val placeEntities = placeJpaRepository.findAllByIdIn(placeIds)
        val placeOneLineReviewStatsMap = placeOneLineReviewStatJpaRepository
            .findAllByPlaceIdIn(placeIds)
            .groupBy { it.placeId }
        return placeEntities.map {
            placePersistenceMapper.toDomain(it, placeOneLineReviewStatsMap[it.id] ?: emptyList())
        }
    }
}
