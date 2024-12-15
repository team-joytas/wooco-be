package kr.wooco.woocobe.course.domain.model

class CourseRegion(
    val primaryRegion: String,
    val secondaryRegion: String,
) {
    companion object {
        fun register(
            primaryRegion: String,
            secondaryRegion: String,
        ): CourseRegion =
            CourseRegion(
                primaryRegion = primaryRegion,
                secondaryRegion = secondaryRegion,
            )
    }
}
