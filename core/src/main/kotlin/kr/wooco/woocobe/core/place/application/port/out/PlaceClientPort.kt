package kr.wooco.woocobe.core.place.application.port.out

interface PlaceClientPort {
    fun fetchPlaceThumbnailUrl(
        name: String,
        address: String,
    ): String?
}
