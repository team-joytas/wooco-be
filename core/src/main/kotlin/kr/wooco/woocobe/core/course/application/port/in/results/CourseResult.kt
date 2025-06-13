package kr.wooco.woocobe.core.course.application.port.`in`.results

import kr.wooco.woocobe.core.course.application.port.out.dto.CourseView
import kr.wooco.woocobe.core.place.domain.entity.Place
import kr.wooco.woocobe.core.user.domain.entity.User
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
            courseView: CourseView,
            user: User,
            places: List<Place>,
            isInterest: Boolean,
        ): CourseResult {
            val placeMap = places.associateBy { it.id }
            return CourseResult(
                id = courseView.id,
                title = courseView.title,
                primaryRegion = courseView.primaryRegion,
                secondaryRegion = courseView.secondaryRegion,
                categories = courseView.categories,
                contents = courseView.contents,
                visitDate = courseView.visitDate,
                comments = courseView.comments,
                interests = courseView.likes,
                createdAt = courseView.createdAt,
                isInterest = isInterest,
                writer = CourseWriterResult(
                    id = user.id,
                    name = user.profile.name,
                    profileUrl = user.profile.profileUrl,
                ),
                places = courseView.coursePlaces.map { coursePlace ->
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
            courseViews: List<CourseView>,
            users: List<User>,
            places: List<Place>,
            interestCourseIds: List<Long>,
        ): List<CourseResult> {
            val userMap = users.associateBy { it.id }
            return courseViews.map { courseView ->
                val user = requireNotNull(userMap[courseView.userId])
                of(
                    courseView,
                    user,
                    places,
                    interestCourseIds.contains(courseView.id),
                )
            }
        }
    }
}
