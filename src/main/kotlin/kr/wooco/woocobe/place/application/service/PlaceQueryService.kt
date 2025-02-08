package kr.wooco.woocobe.place.application.service

import kr.wooco.woocobe.place.application.port.`in`.ReadAllPlaceUseCase
import kr.wooco.woocobe.place.application.port.`in`.ReadPlaceUseCase
import kr.wooco.woocobe.place.application.port.`in`.result.PlaceResult
import kr.wooco.woocobe.place.application.port.out.LoadPlacePersistencePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class PlaceQueryService(
    private val loadPlacePersistencePort: LoadPlacePersistencePort,
) : ReadAllPlaceUseCase,
    ReadPlaceUseCase {
    @Transactional(readOnly = true)
    override fun readAllPlace(query: ReadAllPlaceUseCase.Query): List<PlaceResult> {
        val places = loadPlacePersistencePort.getAllByPlaceIds(query.placeIds)
        return PlaceResult.listFrom(places)
    }

    @Transactional(readOnly = true)
    override fun readPlace(query: ReadPlaceUseCase.Query): PlaceResult {
        val place = loadPlacePersistencePort.getByPlaceId(query.placeId)
        return PlaceResult.from(place)
    }
}
