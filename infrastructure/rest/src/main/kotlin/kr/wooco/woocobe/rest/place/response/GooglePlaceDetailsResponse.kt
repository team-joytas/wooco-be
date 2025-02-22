package kr.wooco.woocobe.rest.place.response

import com.fasterxml.jackson.annotation.JsonProperty

data class GooglePlaceDetailsResponse(
    val result: ResultResponse,
) {
    data class ResultResponse(
        val photos: List<PhotoResponse>,
    ) {
        data class PhotoResponse(
            @JsonProperty("photo_reference") val photoReference: String,
            val width: Long,
        )
    }
}
