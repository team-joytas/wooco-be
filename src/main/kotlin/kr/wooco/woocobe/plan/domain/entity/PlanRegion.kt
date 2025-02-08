package kr.wooco.woocobe.plan.domain.entity

data class PlanRegion(
    val primaryRegion: String,
    val secondaryRegion: String,
) {
    companion object {
        fun create(
            primaryRegion: String,
            secondaryRegion: String,
        ): PlanRegion =
            PlanRegion(
                primaryRegion = primaryRegion,
                secondaryRegion = secondaryRegion,
            )
    }
}
