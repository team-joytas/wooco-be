package kr.wooco.woocobe.core.course.domain.vo

enum class CourseCategory {
    FAMOUS_RESTAURANT,
    ACTIVITY,
    HOT_PLACE,
    CULTURE_AND_ART,
    ETC,
    ;

    companion object {
        operator fun invoke(value: String) = valueOf(value)
    }
}
