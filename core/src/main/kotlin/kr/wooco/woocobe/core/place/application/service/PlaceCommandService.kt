package kr.wooco.woocobe.core.place.application.service

import kr.wooco.woocobe.core.place.application.port.`in`.CreatePlaceIfNotExistsUseCase
import kr.wooco.woocobe.core.place.application.port.`in`.UpdateAverageRatingUseCase
import kr.wooco.woocobe.core.place.application.port.`in`.UpdatePlaceImageUseCase
import kr.wooco.woocobe.core.place.application.port.`in`.UpdateReviewStatsUseCase
import kr.wooco.woocobe.core.place.application.port.out.PlaceClientPort
import kr.wooco.woocobe.core.place.application.port.out.PlaceCommandPort
import kr.wooco.woocobe.core.place.application.port.out.PlaceQueryPort
import kr.wooco.woocobe.core.place.domain.entity.Place
import kr.wooco.woocobe.core.placereview.application.port.out.PlaceReviewQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class PlaceCommandService(
    private val placeClientPort: PlaceClientPort,
    private val placeCommandPort: PlaceCommandPort,
    private val placeQueryPort: PlaceQueryPort,
    private val placeReviewQueryPort: PlaceReviewQueryPort,
) : CreatePlaceIfNotExistsUseCase,
    UpdatePlaceImageUseCase,
    UpdateReviewStatsUseCase,
    UpdateAverageRatingUseCase {
    @Transactional
    override fun createPlaceIfNotExists(command: CreatePlaceIfNotExistsUseCase.Command): Long =
        placeQueryPort.getOrNullByKakaoPlaceId(command.kakaoPlaceId)?.id
            ?: createPlace(command)

    private fun createPlace(command: CreatePlaceIfNotExistsUseCase.Command): Long {
        val place = Place.create(command.toCreateCommand()) { place ->
            placeCommandPort.savePlace(place)
        }
        return place.id
    }

    override fun updatePlaceImage(command: UpdatePlaceImageUseCase.Command) {
        val place = placeQueryPort.getByPlaceId(command.placeId)
        placeClientPort.fetchPlaceThumbnailUrl(place.name, place.address)?.let {
            placeCommandPort.savePlace(place.updateThumbnail(it))
        }
    }

    @Transactional
    override fun updateReviewStats(command: UpdateReviewStatsUseCase.Command) {
        val placeReviewStats = placeReviewQueryPort.getPlaceReviewStatsByPlaceId(command.placeId)
        val place = placeQueryPort
            .getByPlaceId(command.placeId)
            .updatePlaceReviewStats(placeReviewStats.averageRating, placeReviewStats.reviewCount)
        placeCommandPort.savePlace(place)
    }

    @Transactional
    override fun updateAverageRating(command: UpdateAverageRatingUseCase.Command) {
        val averageRating = placeReviewQueryPort.getAverageRatingByPlaceId(command.placeId)
        val place = placeQueryPort
            .getByPlaceId(command.placeId)
            .updateAverageRating(averageRating)
        placeCommandPort.savePlace(place)
    }
}
