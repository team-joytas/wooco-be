package kr.wooco.woocobe.course.domain.model

import kr.wooco.woocobe.course.domain.exception.UnSupportCategoryException

enum class CourseCategory {
    FAMOUS_RESTAURANT,
    ACTIVITY,
    HOT_PLACE,
    CULTURE_AND_ART,
    ETC,
    ;

    companion object {
        fun from(viewName: String) =
            entries.find { it.name == viewName.uppercase() }
                ?: throw UnSupportCategoryException
    }
}
