package kr.wooco.woocobe.placereview.adapter.out.presistence

import kr.wooco.woocobe.placereview.adapter.out.presistence.repository.PlaceOneLineReviewStatJpaRepository
import kr.wooco.woocobe.placereview.application.port.out.LoadPlaceOneLineReviewStatPersistencePort
import kr.wooco.woocobe.placereview.domain.entity.PlaceOneLineReviewStat
import org.springframework.stereotype.Component

// TODO: 한줄평 통계 저장 기능 추가 --- 현재 조회만 가능

@Component
internal class PlaceOneLineReviewPersistenceAdapter(
    private val placeOneLineReviewStatJpaRepository: PlaceOneLineReviewStatJpaRepository,
    private val placeOneLineReviewStatPersistenceMapper: PlaceOneLineReviewStatPersistenceMapper,
) : LoadPlaceOneLineReviewStatPersistencePort {
    override fun getAllStatsByPlaceId(placeId: Long): List<PlaceOneLineReviewStat> {
        val placeOneLineReviewStatEntities = placeOneLineReviewStatJpaRepository.findAllByPlaceId(placeId)
        return placeOneLineReviewStatEntities.map {
            placeOneLineReviewStatPersistenceMapper.toDomain(
                placeOneLineReviewStatJpaEntity = it,
            )
        }
    }

    override fun getAllStatsByPlaceIds(placeIds: List<Long>): List<PlaceOneLineReviewStat> {
        val placeOneLineReviewStatEntities = placeOneLineReviewStatJpaRepository.findAllByPlaceIdIn(placeIds)
        return placeOneLineReviewStatEntities.map {
            placeOneLineReviewStatPersistenceMapper.toDomain(
                placeOneLineReviewStatJpaEntity = it,
            )
        }
    }
}
