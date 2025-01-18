package kr.wooco.woocobe.course.domain.model

import kr.wooco.woocobe.course.domain.exception.InvalidCourseWriterException
import java.time.LocalDate
import java.time.LocalDateTime

class Course(
    val id: Long,
    val userId: Long,
    val region: CourseRegion,
    var categories: List<CourseCategory>,
    var coursePlaces: List<CoursePlace>,
    var title: String,
    var contents: String,
    var visitDate: LocalDate,
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
        title: String,
        categories: List<String>,
        contents: String,
        placeIds: List<Long>,
        visitDate: LocalDate,
    ) = apply {
        this.title = title
        this.categories = categories.map { CourseCategory.from(it) }
        this.contents = contents
        this.coursePlaces = processCoursePlaceOrder(placeIds)
        this.visitDate = visitDate
    }

    fun isValidWriter(userId: Long) {
        if (this.userId != userId) throw InvalidCourseWriterException
    }

    companion object {
        fun register(
            userId: Long,
            region: CourseRegion,
            categories: List<String>,
            placeIds: List<Long>,
            title: String,
            contents: String,
            visitDate: LocalDate,
        ): Course =
            Course(
                id = 0L,
                userId = userId,
                region = region,
                categories = categories.map { CourseCategory.from(it) },
                coursePlaces = processCoursePlaceOrder(placeIds),
                title = title,
                contents = contents,
                visitDate = visitDate,
                views = 0L,
                comments = 0L,
                interests = 0L,
                writeDateTime = LocalDateTime.now(),
            )

        private fun processCoursePlaceOrder(placeIds: List<Long>): List<CoursePlace> =
            placeIds.mapIndexed { index: Int, placeId: Long ->
                CoursePlace(
                    order = index,
                    placeId = placeId,
                )
            }
    }
}
