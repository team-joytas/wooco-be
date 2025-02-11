package kr.wooco.woocobe.course.application.port.`in`.results

import kr.wooco.woocobe.course.domain.entity.Course
import kr.wooco.woocobe.place.domain.entity.Place
import kr.wooco.woocobe.user.domain.entity.User
import java.time.LocalDate
import java.time.LocalDateTime

data class CourseResult(
    val id: Long,
    val title: String,
    val primaryRegion: String,
    val secondaryRegion: String,
    val categories: List<String>,
    val contents: String,
    val visitDate: LocalDate,
    val comments: Long,
    val interests: Long,
    val createdAt: LocalDateTime,
    val isInterest: Boolean,
    val writer: CourseWriterResult,
    val places: List<CoursePlaceResult>,
) {
    data class CoursePlaceResult(
        val order: Int,
        val id: Long,
        val name: String,
        val latitude: Double,
        val longitude: Double,
        val address: String,
        val thumbnailUrl: String,
        val kakaoPlaceId: String,
        val averageRating: Double,
        val reviewCount: Long,
    )

    data class CourseWriterResult(
        val id: Long,
        val name: String,
        val profileUrl: String,
    )

    companion object {
        fun of(
            course: Course,
            user: User,
            places: List<Place>,
            isInterest: Boolean,
        ): CourseResult {
            val placeMap = places.associateBy { it.id }
            return CourseResult(
                id = course.id,
                title = course.title,
                primaryRegion = course.region.primaryRegion,
                secondaryRegion = course.region.secondaryRegion,
                categories = course.categories.map { it.name },
                contents = course.contents,
                visitDate = course.visitDate,
                comments = course.comments,
                interests = course.interests,
                createdAt = course.writeDateTime,
                isInterest = isInterest,
                writer = CourseWriterResult(
                    id = user.id,
                    name = user.profile.name,
                    profileUrl = user.profile.profileUrl,
                ),
                places = course.coursePlaces.map { coursePlace ->
                    val place = requireNotNull(placeMap[coursePlace.placeId])
                    CoursePlaceResult(
                        order = coursePlace.order,
                        id = place.id,
                        name = place.name,
                        latitude = place.latitude,
                        longitude = place.longitude,
                        address = place.address,
                        thumbnailUrl = place.thumbnailUrl,
                        kakaoPlaceId = place.kakaoPlaceId,
                        averageRating = place.averageRating,
                        reviewCount = place.reviewCount,
                    )
                },
            )
        }

        fun listOf(
            courses: List<Course>,
            users: List<User>,
            places: List<Place>,
            interestCourseIds: List<Long>,
        ): List<CourseResult> {
            val userMap = users.associateBy { it.id }
            return courses.map { course ->
                val user = requireNotNull(userMap[course.userId])
                of(course, user, places, interestCourseIds.contains(course.id))
            }
        }
    }
}
