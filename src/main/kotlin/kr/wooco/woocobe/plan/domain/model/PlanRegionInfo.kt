package kr.wooco.woocobe.plan.domain.model

class PlanRegionInfo(
    val primaryRegion: String,
    val secondaryRegion: String,
) {
    companion object {
        fun of(
            primaryRegion: String,
            secondaryRegion: String,
        ): PlanRegionInfo =
            PlanRegionInfo(
                primaryRegion = primaryRegion,
                secondaryRegion = secondaryRegion,
            )
    }
}
