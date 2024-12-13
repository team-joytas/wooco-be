package kr.wooco.woocobe.plan.domain.model

class PlanRegionInfo(
    val majorRegion: String,
    val subRegion: String,
) {
    companion object {
        fun register(
            majorRegion: String,
            subRegion: String,
        ): PlanRegionInfo =
            PlanRegionInfo(
                majorRegion = majorRegion,
                subRegion = subRegion,
            )
    }
}
