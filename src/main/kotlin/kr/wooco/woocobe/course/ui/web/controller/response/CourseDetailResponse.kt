package kr.wooco.woocobe.course.ui.web.controller.response

import kr.wooco.woocobe.course.domain.model.Course
import kr.wooco.woocobe.place.domain.model.Place
import kr.wooco.woocobe.user.domain.model.User
import java.time.LocalDateTime

data class CourseDetailResponse(
    val course: CourseResponse,
    val places: List<CoursePlaceDetailResponse>,
    val writer: CourseWriterDetailResponse,
    val isLiked: Boolean,
) {
    companion object {
        fun of(
            course: Course,
            user: User,
            places: List<Place>,
            isInterest: Boolean,
        ): CourseDetailResponse {
            val placeMap = places.associateBy { it.id }

            return CourseDetailResponse(
                course = CourseResponse.from(course),
                places = course.coursePlaces
                    .map { coursePlace ->
                        val place = requireNotNull(placeMap[coursePlace.placeId])
                        CoursePlaceDetailResponse.of(coursePlace.order, place)
                    },
                writer = CourseWriterDetailResponse.from(user),
                isLiked = isInterest,
            )
        }

        fun listOf(
            courses: List<Course>,
            users: List<User>,
            places: List<Place>,
            interestCourseIds: List<Long>,
        ): List<CourseDetailResponse> {
            val userMap = users.associateBy { it.id }
            val placeMap = places.associateBy { it.id }

            return courses.map { course ->
                val writer = requireNotNull(userMap[course.userId])

                CourseDetailResponse(
                    course = CourseResponse.from(course),
                    places = course.coursePlaces.map { coursePlace ->
                        val place = requireNotNull(placeMap[coursePlace.placeId])
                        CoursePlaceDetailResponse.of(coursePlace.order, place)
                    },
                    writer = CourseWriterDetailResponse.from(writer),
                    isLiked = course.id in interestCourseIds,
                )
            }
        }
    }
}

data class CourseResponse(
    val id: Long,
    val name: String,
    val primaryRegion: String,
    val secondaryRegion: String,
    val categories: List<String>,
    val contents: String,
    val views: Long,
    val comments: Long,
    val likes: Long,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(course: Course): CourseResponse =
            CourseResponse(
                id = course.id,
                name = course.name,
                primaryRegion = course.region.primaryRegion,
                secondaryRegion = course.region.secondaryRegion,
                categories = course.categories.map { it.name },
                contents = course.contents,
                views = course.views,
                comments = course.comments,
                likes = course.interests,
                createdAt = course.writeDateTime,
            )
    }
}

data class CoursePlaceDetailResponse(
    val order: Int,
    val id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val kakaoMapPlaceId: String,
    val averageRating: Double,
    val reviewCount: Long,
) {
    companion object {
        fun of(
            order: Int,
            place: Place,
        ): CoursePlaceDetailResponse =
            CoursePlaceDetailResponse(
                order = order,
                id = place.id,
                name = place.name,
                latitude = place.latitude,
                longitude = place.longitude,
                address = place.address,
                kakaoMapPlaceId = place.kakaoMapPlaceId,
                averageRating = place.averageRating,
                reviewCount = place.reviewCount,
            )
    }
}

data class CourseWriterDetailResponse(
    val id: Long,
    val name: String,
    val profileUrl: String,
) {
    companion object {
        fun from(user: User): CourseWriterDetailResponse =
            CourseWriterDetailResponse(
                id = user.id,
                name = user.name,
                profileUrl = user.profileUrl,
            )
    }
}
