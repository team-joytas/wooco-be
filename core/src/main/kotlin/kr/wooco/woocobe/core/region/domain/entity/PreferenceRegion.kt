package kr.wooco.woocobe.core.region.domain.entity

class PreferenceRegion(
    val id: Long,
    val userId: Long,
    val region: Region,
) {
    constructor(userId: Long, primaryRegion: String, secondaryRegion: String) : this(
        id = 0L,
        userId = userId,
        region = Region(
            primaryRegion = primaryRegion,
            secondaryRegion = secondaryRegion,
        ),
    )

    @JvmRecord
    data class Region(
        val primaryRegion: String,
        val secondaryRegion: String,
    ) {
        init {
            require(primaryRegion.isNotBlank()) { "primaryRegion cannot be blank" }
            require(secondaryRegion.isNotBlank()) { "secondaryRegion cannot be blank" }
        }
    }
}
