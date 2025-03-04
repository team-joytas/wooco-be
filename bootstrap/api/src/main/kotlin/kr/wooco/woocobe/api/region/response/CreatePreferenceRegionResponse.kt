package kr.wooco.woocobe.api.region.response

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
