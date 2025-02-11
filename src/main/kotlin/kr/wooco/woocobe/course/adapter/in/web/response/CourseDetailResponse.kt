package kr.wooco.woocobe.course.adapter.`in`.web.response

import kr.wooco.woocobe.course.application.port.`in`.results.CourseResult
import java.time.LocalDate
import java.time.LocalDateTime

data class CourseDetailResponse(
    val id: Long,
    val title: String,
    val primaryRegion: String,
    val secondaryRegion: String,
    val categories: List<String>,
    val contents: String,
    val visitDate: LocalDate,
    val comments: Long,
    val likes: Long,
    val createdAt: LocalDateTime,
    val places: List<CoursePlaceResponse>,
    val writer: CourseWriterResponse,
    val isLiked: Boolean,
) {
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
    )

    data class CourseWriterResponse(
        val id: Long,
        val name: String,
        val profileUrl: String,
    )

    companion object {
        fun from(result: CourseResult): CourseDetailResponse =
            CourseDetailResponse(
                id = result.id,
                title = result.title,
                primaryRegion = result.primaryRegion,
                secondaryRegion = result.secondaryRegion,
                categories = result.categories,
                contents = result.contents,
                visitDate = result.visitDate,
                comments = result.comments,
                likes = result.interests,
                createdAt = result.createdAt,
                places = result.places.map {
                    CoursePlaceResponse(
                        order = it.order,
                        id = it.id,
                        name = it.name,
                        latitude = it.latitude,
                        longitude = it.longitude,
                        address = it.address,
                        thumbnailUrl = it.thumbnailUrl,
                        kakaoMapPlaceId = it.kakaoPlaceId,
                        averageRating = it.averageRating,
                        reviewCount = it.reviewCount,
                    )
                },
                writer = CourseWriterResponse(
                    id = result.writer.id,
                    name = result.writer.name,
                    profileUrl = result.writer.profileUrl,
                ),
                isLiked = result.isInterest,
            )

        fun listFrom(results: List<CourseResult>): List<CourseDetailResponse> = results.map { from(it) }
    }
}
