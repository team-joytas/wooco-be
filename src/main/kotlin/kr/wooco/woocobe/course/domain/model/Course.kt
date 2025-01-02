package kr.wooco.woocobe.course.domain.model

import java.time.LocalDateTime

class Course(
    val id: Long,
    val userId: Long,
    val region: CourseRegion,
    var categories: List<CourseCategory>,
    var name: String,
    var contents: String,
    var views: Long,
    var comments: Long,
    var interests: Long,
    val writeDateTime: LocalDateTime,
) {
    fun increaseViews() {
        views++
    }

    fun increaseComments() {
        comments++
    }

    fun decreaseComments() {
        comments--
    }

    fun increaseInterests() {
        interests++
    }

    fun decreaseInterests() {
        interests--
    }

    fun update(
        name: String,
        categories: List<String>,
        contents: String,
    ) = apply {
        this.name = name
        this.categories = categories.map { CourseCategory.from(it) }
        this.contents = contents
    }

    fun isValidWriter(userId: Long) {
        if (this.userId != userId) {
            throw RuntimeException()
        }
    }

    companion object {
        fun register(
            userId: Long,
            region: CourseRegion,
            categories: List<String>,
            name: String,
            contents: String,
        ): Course =
            Course(
                id = 0L,
                userId = userId,
                region = region,
                categories = categories.map { CourseCategory.from(it) },
                name = name,
                contents = contents,
                views = 0L,
                comments = 0L,
                interests = 0L,
                writeDateTime = LocalDateTime.now(),
            )
    }
}
