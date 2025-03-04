package kr.wooco.woocobe.rest.place

import kr.wooco.woocobe.rest.place.response.GooglePlaceDetailsResponse
import kr.wooco.woocobe.rest.place.response.GooglePlaceIdResponse
import org.springframework.http.ResponseEntity
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange

interface GooglePlaceApiClient {
    @GetExchange(url = GOOGLE_FIND_PLACE_URL)
    fun fetchGooglePlaceId(
        @RequestParam params: MultiValueMap<String, String>,
    ): GooglePlaceIdResponse

    @GetExchange(url = GOOGLE_PLACE_DETAILS_URL)
    fun fetchGooglePlaceDetails(
        @RequestParam params: MultiValueMap<String, String>,
    ): GooglePlaceDetailsResponse

    @GetExchange(url = GOOGLE_PLACE_PHOTO_URL)
    fun fetchGooglePlacePhotoUrl(
        @RequestParam params: MultiValueMap<String, String>,
    ): ResponseEntity<Void>

    companion object {
        private const val GOOGLE_FIND_PLACE_URL = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json"
        private const val GOOGLE_PLACE_DETAILS_URL = "https://maps.googleapis.com/maps/api/place/details/json"
        private const val GOOGLE_PLACE_PHOTO_URL = "https://maps.googleapis.com/maps/api/place/photo"
    }
}
