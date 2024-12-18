package kr.wooco.woocobe.plan.domain.model

class PlanRegion(
    val primaryRegion: String,
    val secondaryRegion: String,
) {
    companion object {
        fun register(
            primaryRegion: String,
            secondaryRegion: String,
        ): PlanRegion =
            PlanRegion(
                primaryRegion = primaryRegion,
                secondaryRegion = secondaryRegion,
            )
    }
}
