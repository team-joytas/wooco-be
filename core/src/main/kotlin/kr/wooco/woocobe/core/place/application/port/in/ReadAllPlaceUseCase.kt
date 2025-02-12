package kr.wooco.woocobe.core.place.application.port.`in`

import kr.wooco.woocobe.core.place.application.port.`in`.result.PlaceResult

fun interface ReadAllPlaceUseCase {
    data class Query(
        val placeIds: List<Long>,
    )

    fun readAllPlace(query: Query): List<PlaceResult>
}
