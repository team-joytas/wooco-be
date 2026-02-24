package kr.wooco.woocobe.mysql.place

import kr.wooco.woocobe.core.place.application.port.out.PlaceCommandPort
import kr.wooco.woocobe.core.place.application.port.out.PlaceQueryPort
import kr.wooco.woocobe.core.place.domain.entity.Place
import kr.wooco.woocobe.core.place.domain.exception.NotExistsPlaceException
import kr.wooco.woocobe.mysql.place.repository.PlaceJpaRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Component
internal class PlacePersistenceAdapter(
    private val placeJpaRepository: PlaceJpaRepository,
) : PlaceQueryPort,
    PlaceCommandPort {
    @Transactional
    override fun savePlace(place: Place): Long {
        val placeEntity = PlacePersistenceMapper.toJpaEntity(place)
        val savedPlaceEntity = placeJpaRepository.save(placeEntity)
        return savedPlaceEntity.id
    }

    @Transactional
    override fun markThumbnailRefreshed(placeId: Long) {
        placeJpaRepository.markThumbnailRefreshed(
            placeId = placeId,
            now = LocalDateTime.now(),
        )
    }

    @Transactional
    override fun markThumbnailRefreshFailed(placeId: Long) {
        placeJpaRepository.markThumbnailRefreshFailed(placeId = placeId)
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

    override fun getPlaceIdsNeedingThumbnailRefresh(limit: Int): List<Long> {
        val threshold = LocalDateTime.now().minusDays(TTL_DAYS)
        return placeJpaRepository.findPlaceIdsNeedingThumbnailRefresh(
            threshold = threshold,
            maxRetry = MAX_RETRY_COUNT,
            pageable = PageRequest.of(0, limit),
        )
    }

    private companion object {
        const val TTL_DAYS = 30L
        const val MAX_RETRY_COUNT = 3
    }
}
