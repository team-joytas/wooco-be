package kr.wooco.woocobe.core.place.application.service

import kr.wooco.woocobe.core.place.application.port.`in`.ReadAllPlaceUseCase
import kr.wooco.woocobe.core.place.application.port.`in`.ReadPlaceUseCase
import kr.wooco.woocobe.core.place.application.port.`in`.result.PlaceResult
import kr.wooco.woocobe.core.place.application.port.out.LoadPlacePersistencePort
import kr.wooco.woocobe.core.placereview.application.port.out.LoadPlaceOneLineReviewPersistencePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class PlaceQueryService(
    private val loadPlacePersistencePort: LoadPlacePersistencePort,
    private val loadPlaceOneLineReviewPersistencePort: LoadPlaceOneLineReviewPersistencePort,
) : ReadAllPlaceUseCase,
    ReadPlaceUseCase {
    @Transactional(readOnly = true)
    override fun readAllPlace(query: ReadAllPlaceUseCase.Query): List<PlaceResult> {
        val places = loadPlacePersistencePort.getAllByPlaceIds(query.placeIds)
        return PlaceResult.listOf(places)
    }

    @Transactional(readOnly = true)
    override fun readPlace(query: ReadPlaceUseCase.Query): PlaceResult {
        val place = loadPlacePersistencePort.getByPlaceId(query.placeId)
        val placeOneLineReviewStats =
            loadPlaceOneLineReviewPersistencePort.getAllPlaceOneLineReviewStatsByPlaceId(query.placeId)
        return PlaceResult.of(place, placeOneLineReviewStats)
    }
}
