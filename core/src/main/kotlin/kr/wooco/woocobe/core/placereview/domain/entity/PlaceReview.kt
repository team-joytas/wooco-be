package kr.wooco.woocobe.core.placereview.domain.entity

import kr.wooco.woocobe.core.common.domain.entity.AggregateRoot
import kr.wooco.woocobe.core.placereview.domain.command.CreatePlaceReviewCommand
import kr.wooco.woocobe.core.placereview.domain.command.DeletePlaceReviewCommand
import kr.wooco.woocobe.core.placereview.domain.command.UpdatePlaceReviewCommand
import kr.wooco.woocobe.core.placereview.domain.event.PlaceReviewCreatedEvent
import kr.wooco.woocobe.core.placereview.domain.event.PlaceReviewDeletedEvent
import kr.wooco.woocobe.core.placereview.domain.event.PlaceReviewUpdatedEvent
import kr.wooco.woocobe.core.placereview.domain.exception.InvalidPlaceReviewWriterException
import kr.wooco.woocobe.core.placereview.domain.exception.NotExistsPlaceReviewException
import kr.wooco.woocobe.core.placereview.domain.vo.PlaceOneLineReview
import kr.wooco.woocobe.core.placereview.domain.vo.PlaceReviewContent
import kr.wooco.woocobe.core.placereview.domain.vo.PlaceReviewRating
import java.time.LocalDateTime

data class PlaceReview(
    override val id: Long,
    val userId: Long,
    val placeId: Long,
    val writeDateTime: LocalDateTime,
    val rating: PlaceReviewRating,
    val contents: PlaceReviewContent,
    val oneLineReviews: List<PlaceOneLineReview>,
    val imageUrls: List<String>,
    val status: Status,
    // TODO: 썸네일 처리 기능 구현 예정
) : AggregateRoot() {
    init {
        require(imageUrls.size <= 10) { "이미지는 최대 10개까지 등록할 수 있습니다." }
    }

    enum class Status {
        ACTIVE,
        DELETED,
    }

    fun update(command: UpdatePlaceReviewCommand): PlaceReview {
        validateAuthor(command.userId)
        return copy(
            rating = command.rating,
            contents = command.contents,
            oneLineReviews = command.oneLineReviews.distinctBy { it.contents },
            imageUrls = command.imageUrls,
        ).also {
            it.registerEvent(
                PlaceReviewUpdatedEvent.from(it),
            )
        }
    }

    fun delete(command: DeletePlaceReviewCommand): PlaceReview {
        validateAuthor(command.userId)
        return copy(
            status = Status.DELETED,
        ).also {
            it.registerEvent(
                PlaceReviewDeletedEvent.from(it),
            )
        }
    }

    private fun validateAuthor(userId: Long) {
        when {
            this.status == Status.DELETED -> throw NotExistsPlaceReviewException
            this.userId != userId -> throw InvalidPlaceReviewWriterException
        }
    }

    companion object {
        fun create(
            command: CreatePlaceReviewCommand,
            identifier: (PlaceReview) -> Long,
        ): PlaceReview =
            PlaceReview(
                id = 0L,
                userId = command.userId,
                placeId = command.placeId,
                writeDateTime = LocalDateTime.now(),
                rating = command.rating,
                contents = command.contents,
                oneLineReviews = command.oneLineReviews.distinctBy { it.contents },
                imageUrls = command.imageUrls,
                status = Status.ACTIVE,
            ).let {
                it.copy(id = identifier.invoke(it))
            }.also {
                it.registerEvent(
                    PlaceReviewCreatedEvent.from(it),
                )
            }
    }
}
