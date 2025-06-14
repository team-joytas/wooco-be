package kr.wooco.woocobe.core.region.domain.vo

data class Region(
    val primaryRegion: String,
    val secondaryRegion: String,
) {
    init {
        require(primaryRegion.isNotBlank() && secondaryRegion.isNotBlank()) { "지역 정보는 빈값이 될 수 없습니다." }
    }
}
