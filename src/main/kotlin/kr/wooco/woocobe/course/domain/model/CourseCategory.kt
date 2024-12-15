package kr.wooco.woocobe.course.domain.model

enum class CourseCategory {
    FAMOUS_RESTAURANT,
    ACTIVITY,
    HOT_PLACE,
    CULTURE_AND_ART,
    ETC,
    ;

    companion object {
        fun from(viewName: String) =
            entries.find { it.name == viewName }
                ?: throw RuntimeException("not found course category $viewName")
    }
}
