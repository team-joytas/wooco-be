package kr.wooco.woocobe.rest.place

import kr.wooco.woocobe.core.place.application.port.out.PlaceClientPort
import kr.wooco.woocobe.rest.place.response.GooglePlaceDetailsResponse.ResultResponse.PhotoResponse
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap

@Component
internal class PlaceClientAdapter(
    private val googlePlaceApiClient: GooglePlaceApiClient,
    private val googlePlaceProperties: GooglePlaceProperties,
) : PlaceClientPort {
    override fun fetchPlaceThumbnailUrl(
        name: String,
        address: String,
    ): String? =
        getFirstGooglePlaceId(name, address)
            ?.let { getFirstPhotoReference(it) }
            ?.let { getPhotoUrl(it.width, it.photoReference) }

    private fun getFirstGooglePlaceId(
        name: String,
        address: String,
    ): String? {
        val params = LinkedMultiValueMap<String, String>().apply {
            add("input", "$address \"$name\"")
            add("key", googlePlaceProperties.apiKey)
            add("inputtype", "textquery")
        }
        val response = googlePlaceApiClient.fetchGooglePlaceId(params)
        return response.candidates.map { it.placeId }.firstOrNull()
    }

    private fun getFirstPhotoReference(googlePlaceId: String): PhotoResponse? {
        val params = LinkedMultiValueMap<String, String>().apply {
            add("placeid", googlePlaceId)
            add("key", googlePlaceProperties.apiKey)
            add("language", "ko")
        }
        val response = googlePlaceApiClient.fetchGooglePlaceDetails(params)
        return response.result.photos.firstOrNull()
    }

    private fun getPhotoUrl(
        photoWidth: Long,
        photoReference: String,
    ): String? {
        val params = LinkedMultiValueMap<String, String>().apply {
            add("photo_reference", photoReference)
            add("key", googlePlaceProperties.apiKey)
            add("maxwidth", "$photoWidth")
        }
        val response = googlePlaceApiClient.fetchGooglePlacePhotoUrl(params)
        return response.headers.location?.toString()
    }
}
