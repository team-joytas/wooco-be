package kr.wooco.woocobe.place.application.service

import kr.wooco.woocobe.place.application.port.`in`.ReadAllPlaceUseCase
import kr.wooco.woocobe.place.application.port.`in`.ReadPlaceUseCase
import kr.wooco.woocobe.place.application.port.`in`.result.PlaceResult
import kr.wooco.woocobe.place.application.port.out.LoadPlacePersistencePort
import kr.wooco.woocobe.placereview.application.port.out.LoadPlaceOneLineReviewStatPersistencePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class PlaceQueryService(
    private val loadPlacePersistencePort: LoadPlacePersistencePort,
    private val loadPlaceOneLineReviewStatsPersistencePort: LoadPlaceOneLineReviewStatPersistencePort,
) : ReadAllPlaceUseCase,
    ReadPlaceUseCase {
    @Transactional(readOnly = true)
    override fun readAllPlace(query: ReadAllPlaceUseCase.Query): List<PlaceResult> {
        val places = loadPlacePersistencePort.getAllByPlaceIds(query.placeIds)
        val placeOneLineReviewStats = loadPlaceOneLineReviewStatsPersistencePort.getAllStatsByPlaceIds(query.placeIds)
        return PlaceResult.listOf(places, placeOneLineReviewStats)
    }

    @Transactional(readOnly = true)
    override fun readPlace(query: ReadPlaceUseCase.Query): PlaceResult {
        val place = loadPlacePersistencePort.getByPlaceId(query.placeId)
        val placeOneLineReviewStats = loadPlaceOneLineReviewStatsPersistencePort.getAllStatsByPlaceId(query.placeId)
        return PlaceResult.of(place, placeOneLineReviewStats)
    }
}
