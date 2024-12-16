package kr.wooco.woocobe.course.domain.model

enum class CourseSortCondition {
    POPULAR,
    RECENT,
    ;

    companion object {
        fun from(value: String): CourseSortCondition =
            entries.find { it.name == value }
                ?: throw RuntimeException()
    }
}
