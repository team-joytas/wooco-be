package kr.wooco.woocobe.course.domain.model

import kr.wooco.woocobe.course.domain.exception.UnSupportSortConditionException

enum class CourseSortCondition {
    POPULAR,
    RECENT,
    ;

    companion object {
        fun from(value: String): CourseSortCondition =
            entries.find { it.name == value.uppercase() }
                ?: throw UnSupportSortConditionException
    }
}
