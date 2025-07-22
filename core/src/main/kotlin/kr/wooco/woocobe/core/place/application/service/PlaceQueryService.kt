package kr.wooco.woocobe.core.place.application.service

import kr.wooco.woocobe.core.place.application.port.`in`.ReadAllPlaceUseCase
import kr.wooco.woocobe.core.place.application.port.`in`.ReadPlaceUseCase
import kr.wooco.woocobe.core.place.application.port.`in`.ReadPlaceWithPlaceReviewsUseCase
import kr.wooco.woocobe.core.place.application.port.`in`.result.PlaceResult
import kr.wooco.woocobe.core.place.application.port.`in`.result.PlaceWithPlaceReviewsResult
import kr.wooco.woocobe.core.place.application.port.out.PlaceQueryPort
import kr.wooco.woocobe.core.placereview.application.port.out.PlaceReviewQueryPort
import kr.wooco.woocobe.core.user.application.port.out.UserQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class PlaceQueryService(
    private val placeQueryPort: PlaceQueryPort,
    private val placeReviewQueryPort: PlaceReviewQueryPort,
    private val userQueryPort: UserQueryPort,
) : ReadAllPlaceUseCase,
    ReadPlaceUseCase,
    ReadPlaceWithPlaceReviewsUseCase {
    @Transactional(readOnly = true)
    override fun readAllPlace(query: ReadAllPlaceUseCase.Query): List<PlaceResult> {
        val places = placeQueryPort.getAllByPlaceIds(query.placeIds)
        return PlaceResult.listOf(places)
    }

    @Transactional(readOnly = true)
    override fun readPlace(query: ReadPlaceUseCase.Query): PlaceResult {
        val place = placeQueryPort.getByPlaceId(query.placeId)
        val placeOneLineReviewStats = placeReviewQueryPort.getAllPlaceOneLineReviewStatsByPlaceId(query.placeId)
        return PlaceResult.of(place, placeOneLineReviewStats)
    }

    @Transactional(readOnly = true)
    override fun readPlaceWithPlaceReviews(query: ReadPlaceWithPlaceReviewsUseCase.Query): PlaceWithPlaceReviewsResult {
        val place = placeQueryPort.getByPlaceId(query.placeId)
        val placeOneLineReviewStats = placeReviewQueryPort.getAllPlaceOneLineReviewStatsByPlaceId(query.placeId)
        val placeReviews = placeReviewQueryPort.getTop2ByPlaceId(query.placeId)
        val writerIds = placeReviews.map { it.userId }.distinct()
        val writers = userQueryPort.getAllByUserIds(writerIds)
        return PlaceWithPlaceReviewsResult.of(place, placeOneLineReviewStats, placeReviews, writers)
    }
}
