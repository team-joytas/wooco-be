package kr.wooco.woocobe.place.application.port.`in`

import kr.wooco.woocobe.place.application.port.`in`.result.PlaceResult

fun interface ReadPlaceUseCase {
    data class Query(
        val placeId: Long,
    )

    fun readPlace(query: Query): PlaceResult
}
