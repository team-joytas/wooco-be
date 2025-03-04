package kr.wooco.woocobe.rest.place.response

import com.fasterxml.jackson.annotation.JsonProperty

data class GooglePlaceIdResponse(
    val candidates: List<CandidatesResponse>,
) {
    data class CandidatesResponse(
        @JsonProperty("place_id") val placeId: String,
    )
}
