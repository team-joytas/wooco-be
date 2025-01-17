package kr.wooco.woocobe.course.ui.web.controller.response

import kr.wooco.woocobe.course.domain.model.Course
import kr.wooco.woocobe.place.domain.model.Place
import kr.wooco.woocobe.user.domain.model.User
import java.time.LocalDate
import java.time.LocalDateTime

data class CourseDetailResponse(
    val id: Long,
    val name: String,
    val primaryRegion: String,
    val secondaryRegion: String,
    val categories: List<String>,
    val contents: String,
    val visitDate: LocalDate,
    val views: Long,
    val comments: Long,
    val likes: Long,
    val createdAt: LocalDateTime,
    val places: List<CoursePlaceResponse>,
    val writer: CourseWriterResponse,
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
                id = course.id,
                name = course.name,
                primaryRegion = course.region.primaryRegion,
                secondaryRegion = course.region.secondaryRegion,
                categories = course.categories.map { it.name },
                contents = course.contents,
                visitDate = course.visitDate,
                views = course.views,
                comments = course.comments,
                likes = course.interests,
                createdAt = course.writeDateTime,
                places = course.coursePlaces
                    .map { coursePlace ->
                        val place = requireNotNull(placeMap[coursePlace.placeId])
                        CoursePlaceResponse.of(coursePlace.order, place)
                    },
                writer = CourseWriterResponse.from(user),
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
                    id = course.id,
                    name = course.name,
                    primaryRegion = course.region.primaryRegion,
                    secondaryRegion = course.region.secondaryRegion,
                    categories = course.categories.map { it.name },
                    contents = course.contents,
                    visitDate = course.visitDate,
                    views = course.views,
                    comments = course.comments,
                    likes = course.interests,
                    createdAt = course.writeDateTime,
                    places = course.coursePlaces.map { coursePlace ->
                        val place = requireNotNull(placeMap[coursePlace.placeId])
                        CoursePlaceResponse.of(coursePlace.order, place)
                    },
                    writer = CourseWriterResponse.from(writer),
                    isLiked = course.id in interestCourseIds,
                )
            }
        }
    }
}

data class CoursePlaceResponse(
    val order: Int,
    val id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val thumbnailUrl: String,
    val kakaoMapPlaceId: String,
    val averageRating: Double,
    val reviewCount: Long,
) {
    companion object {
        fun of(
            order: Int,
            place: Place,
        ): CoursePlaceResponse =
            CoursePlaceResponse(
                order = order,
                id = place.id,
                name = place.name,
                latitude = place.latitude,
                longitude = place.longitude,
                address = place.address,
                thumbnailUrl = place.thumbnailUrl,
                kakaoMapPlaceId = place.kakaoMapPlaceId,
                averageRating = place.averageRating,
                reviewCount = place.reviewCount,
            )
    }
}

data class CourseWriterResponse(
    val id: Long,
    val name: String,
    val profileUrl: String,
) {
    companion object {
        fun from(user: User): CourseWriterResponse =
            CourseWriterResponse(
                id = user.id,
                name = user.name,
                profileUrl = user.profileUrl,
            )
    }
}
