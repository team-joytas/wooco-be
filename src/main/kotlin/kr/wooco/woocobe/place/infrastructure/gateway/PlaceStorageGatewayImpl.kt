package kr.wooco.woocobe.place.infrastructure.gateway

import kr.wooco.woocobe.place.domain.exception.MissingPlaceOneLineReviewContentException
import kr.wooco.woocobe.place.domain.exception.MissingPlaceOneLineReviewCountException
import kr.wooco.woocobe.place.domain.exception.NotExistsPlaceException
import kr.wooco.woocobe.place.domain.gateway.PlaceStorageGateway
import kr.wooco.woocobe.place.domain.model.Place
import kr.wooco.woocobe.place.domain.model.PlaceOneLineReviewStat
import kr.wooco.woocobe.place.infrastructure.storage.PlaceOneLineReviewStatStorageMapper
import kr.wooco.woocobe.place.infrastructure.storage.PlaceStorageMapper
import kr.wooco.woocobe.place.infrastructure.storage.repository.PlaceJpaRepository
import kr.wooco.woocobe.place.infrastructure.storage.repository.PlaceOneLineReviewJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class PlaceStorageGatewayImpl(
    private val placeJpaRepository: PlaceJpaRepository,
    private val placeOneLineReviewRepository: PlaceOneLineReviewJpaRepository,
    private val placeStorageMapper: PlaceStorageMapper,
    private val placeOneLineReviewStatStorageMapper: PlaceOneLineReviewStatStorageMapper,
) : PlaceStorageGateway {
    override fun save(place: Place): Place {
        val placeEntity = placeStorageMapper.toEntity(place)
        placeJpaRepository.save(placeEntity)

        return placeStorageMapper.toDomain(placeEntity)
    }

    override fun getByPlaceId(placeId: Long): Place {
        val placeEntity = placeJpaRepository.findByIdOrNull(placeId)
            ?: throw NotExistsPlaceException

        return placeStorageMapper.toDomain(placeEntity)
    }

    override fun getOrNullByKakaoMapPlaceId(kakaoMapPlaceId: String): Place? {
        val placeEntity = placeJpaRepository.findByKakaoMapPlaceId(kakaoMapPlaceId)

        return placeEntity?.let { placeStorageMapper.toDomain(it) }
    }

    override fun getAllByPlaceIds(placeIds: List<Long>): List<Place> {
        val placeEntities = placeJpaRepository.findAllByIdIn(placeIds)

        return placeEntities.map { placeStorageMapper.toDomain(it) }
    }

    override fun getOneLineReviewStats(placeId: Long): List<PlaceOneLineReviewStat> {
        val stats = placeOneLineReviewRepository.findPlaceOneLineReviewStatsByPlaceId(placeId)

        return stats.map { row ->
            val content = row[CONTENT] ?: throw MissingPlaceOneLineReviewContentException
            val count = row[COUNT] ?: throw MissingPlaceOneLineReviewCountException

            placeOneLineReviewStatStorageMapper.toDomain(content.toString(), count)
        }
    }

    companion object {
        private const val CONTENT = "content"
        private const val COUNT = "count"
    }
}
