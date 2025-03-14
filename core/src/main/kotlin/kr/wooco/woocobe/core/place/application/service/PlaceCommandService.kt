package kr.wooco.woocobe.core.place.application.service

import kr.wooco.woocobe.core.place.application.port.`in`.CreatePlaceIfNotExistsUseCase
import kr.wooco.woocobe.core.place.application.port.`in`.DecreaseReviewCountUseCase
import kr.wooco.woocobe.core.place.application.port.`in`.IncreasePlaceReviewCountUseCase
import kr.wooco.woocobe.core.place.application.port.`in`.ProcessAverageRatingUseCase
import kr.wooco.woocobe.core.place.application.port.`in`.UpdatePlaceImageUseCase
import kr.wooco.woocobe.core.place.application.port.out.LoadPlacePersistencePort
import kr.wooco.woocobe.core.place.application.port.out.PlaceClientPort
import kr.wooco.woocobe.core.place.application.port.out.SavePlacePersistencePort
import kr.wooco.woocobe.core.place.domain.entity.Place
import kr.wooco.woocobe.core.place.domain.event.PlaceCreateEvent
import kr.wooco.woocobe.core.placereview.application.port.out.LoadPlaceReviewPersistencePort
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class PlaceCommandService(
    private val eventPublisher: ApplicationEventPublisher,
    private val placeClientPort: PlaceClientPort,
    private val savePlacePersistencePort: SavePlacePersistencePort,
    private val loadPlacePersistencePort: LoadPlacePersistencePort,
    private val loadPlaceReviewPersistencePort: LoadPlaceReviewPersistencePort,
) : CreatePlaceIfNotExistsUseCase,
    UpdatePlaceImageUseCase,
    IncreasePlaceReviewCountUseCase,
    ProcessAverageRatingUseCase,
    DecreaseReviewCountUseCase {
    @Transactional
    override fun createPlaceIfNotExists(command: CreatePlaceIfNotExistsUseCase.Command): Long =
        loadPlacePersistencePort.getOrNullByKakaoPlaceId(command.kakaoPlaceId)?.id
            ?: createPlace(command)

    private fun createPlace(command: CreatePlaceIfNotExistsUseCase.Command): Long {
        val place = savePlacePersistencePort.savePlace(
            Place.create(
                name = command.name,
                kakaoPlaceId = command.kakaoPlaceId,
                address = command.address,
                latitude = command.latitude,
                longitude = command.longitude,
                phoneNumber = command.phoneNumber,
            ),
        )
        eventPublisher.publishEvent(PlaceCreateEvent.from(place))
        return place.id
    }

    // NOTE - Hong: 외부 API 호출이 연동되어있어 Adapter 레이어에서 Transactional 정의
    override fun updatePlaceImage(command: UpdatePlaceImageUseCase.Command) {
        val place = loadPlacePersistencePort.getByPlaceId(command.placeId)
        placeClientPort.fetchPlaceThumbnailUrl(place.name, place.address)?.let {
            savePlacePersistencePort.savePlace(place.updateThumbnail(it))
        }
    }

    @Transactional
    override fun increasePlaceReviewCount(command: IncreasePlaceReviewCountUseCase.Command) {
        val place = loadPlacePersistencePort
            .getByPlaceId(command.placeId)
            .increaseReviewCounts()
        savePlacePersistencePort.savePlace(place)
    }

    @Transactional
    override fun processAverageRating(command: ProcessAverageRatingUseCase.Command) {
        val averageRating = loadPlaceReviewPersistencePort.getAverageRatingByPlaceId(command.placeId)
        val place = loadPlacePersistencePort
            .getByPlaceId(command.placeId)
            .updateAverageRating(averageRating)
        savePlacePersistencePort.savePlace(place)
    }

    @Transactional
    override fun decreaseReviewCount(command: DecreaseReviewCountUseCase.Command) {
        val place = loadPlacePersistencePort
            .getByPlaceId(command.placeId)
            .decreaseReviewCounts()
        savePlacePersistencePort.savePlace(place)
    }
}
