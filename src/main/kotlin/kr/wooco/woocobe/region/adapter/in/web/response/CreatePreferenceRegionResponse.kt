package kr.wooco.woocobe.region.adapter.`in`.web.response

data class CreatePreferenceRegionResponse(
    val id: Long,
) {
    companion object {
        fun from(preferenceRegionId: Long): CreatePreferenceRegionResponse =
            CreatePreferenceRegionResponse(
                id = preferenceRegionId,
            )
    }
}
