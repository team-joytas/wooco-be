package kr.wooco.woocobe.course.domain.model

import kr.wooco.woocobe.user.domain.model.User
import java.time.LocalDateTime

class Course(
    val id: Long,
    val user: User,
    val region: CourseRegion,
    val writeDateTime: LocalDateTime,
    var categories: List<CourseCategory>,
    var name: String,
    var contents: String,
    var views: Long,
    var comments: Long,
    var interests: Long,
) {
    fun increaseViews() =
        apply {
            views++
        }

    fun increaseComments() =
        apply {
            comments++
        }

    fun decreaseComments() =
        apply {
            comments--
        }

    fun increaseInterests() =
        apply {
            interests++
        }

    fun decreaseInterests() =
        apply {
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

    fun isWriter(targetId: Long): Boolean =
        when (user.id == targetId) {
            true -> true
            else -> throw RuntimeException()
        }

    companion object {
        fun register(
            user: User,
            region: CourseRegion,
            categories: List<String>,
            name: String,
            contents: String,
        ): Course =
            Course(
                id = 0L,
                user = user,
                region = region,
                categories = categories.map { CourseCategory.from(it) },
                writeDateTime = LocalDateTime.now(),
                name = name,
                contents = contents,
                views = 0L,
                comments = 0L,
                interests = 0L,
            )
    }
}
