package kr.wooco.woocobe.core.place.domain.entity

import kr.wooco.woocobe.core.common.domain.entity.AggregateRoot
import kr.wooco.woocobe.core.place.domain.command.CreatePlaceCommand

data class Place(
    override val id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val kakaoPlaceId: String,
    val averageRating: Double,
    val reviewCount: Long,
    val phoneNumber: String,
    val thumbnailUrl: String,
    // TODO: 썸네일 처리 기능 구현 예정
) : AggregateRoot() {
    init {
        require(reviewCount >= 0) { "리뷰 수는 0 미만으로 설정할 수 없습니다." }
    }

    fun updateThumbnail(thumbnailUrl: String): Place =
        copy(
            thumbnailUrl = thumbnailUrl,
        )

    fun updateAverageRating(averageRating: Double): Place =
        copy(
            averageRating = averageRating,
        )

    fun updatePlaceReviewStats(
        averageRating: Double,
        reviewCount: Long,
    ): Place =
        copy(
            averageRating = averageRating,
            reviewCount = reviewCount,
        )

    companion object {
        fun create(
            command: CreatePlaceCommand,
            identifier: (Place) -> Long,
        ): Place =
            Place(
                id = 0L,
                name = command.name,
                latitude = command.latitude,
                longitude = command.longitude,
                address = command.address,
                kakaoPlaceId = command.kakaoPlaceId,
                averageRating = 0.0,
                reviewCount = 0,
                phoneNumber = command.phoneNumber,
                thumbnailUrl = "",
            ).let {
                it.copy(id = identifier.invoke(it))
            }
    }
}
