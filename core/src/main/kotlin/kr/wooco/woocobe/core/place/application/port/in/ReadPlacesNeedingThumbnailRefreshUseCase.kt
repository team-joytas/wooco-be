package kr.wooco.woocobe.core.place.application.port.`in`

fun interface ReadPlacesNeedingThumbnailRefreshUseCase {
    data class Query(
        val limit: Int,
    )

    fun readPlacesNeedingThumbnailRefresh(query: Query): List<Long>
}
