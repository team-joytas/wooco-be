package kr.wooco.woocobe.place.ui.web.controller.response

import kr.wooco.woocobe.place.domain.usecase.GetAllPlaceReviewOutput

data class GetAllPlaceReviewResponse(
    val placeReviews: List<PlaceReviewDetailsResponse>,
) {
    companion object {
        fun of(getAllPlaceReviewOutput: GetAllPlaceReviewOutput): GetAllPlaceReviewResponse =
            GetAllPlaceReviewResponse(
                placeReviews = getAllPlaceReviewOutput.placeReviews.map { placeReview ->
                    PlaceReviewDetailsResponse(
                        placeReviewId = placeReview.id,
                        userId = placeReview.userId,
                        rating = placeReview.rating,
                        content = placeReview.content,
                        oneLineReviews = placeReview.oneLineReviews
                            .map { oneLineReview ->
                                PlaceOneLineReviewResponse.from(oneLineReview.content)
                            },
                        imageUrls = placeReview.imageUrls,
                    )
                },
            )
    }
}
