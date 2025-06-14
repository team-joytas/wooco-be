package kr.wooco.woocobe.core.course.domain.entity

import kr.wooco.woocobe.core.common.domain.entity.AggregateRoot
import kr.wooco.woocobe.core.course.domain.command.CreateCourseCommand
import kr.wooco.woocobe.core.course.domain.command.DeleteCourseCommand
import kr.wooco.woocobe.core.course.domain.command.UpdateCourseInfoCommand
import kr.wooco.woocobe.core.course.domain.exception.InvalidCourseWriterException
import kr.wooco.woocobe.core.course.domain.exception.NotExistsCourseException
import kr.wooco.woocobe.core.course.domain.vo.CourseCategory
import kr.wooco.woocobe.core.course.domain.vo.CourseContent
import kr.wooco.woocobe.core.course.domain.vo.CourseRegion
import java.time.LocalDate

// TODO: 비공개 | 공개 추가 예정

data class Course(
    override val id: Long,
    val userId: Long,
    val region: CourseRegion,
    val content: CourseContent,
    val places: List<CoursePlace>,
    val categories: List<CourseCategory>, // TODO: 해시태그로 변경 예정
    val visitDate: VisitDate,
    val status: Status,
) : AggregateRoot() {
    init {
        require(places.size in 1..5) { "장소는 1개 이상 5개 이하여야 합니다." }
    }

    @JvmInline
    value class VisitDate(
        val value: LocalDate,
    ) {
        init {
            require(!value.isAfter(LocalDate.now())) { "방문 날짜는 금일 날짜를 넘을 수 없습니다." }
        }
    }

    enum class Status {
        ACTIVE,
        DELETED,
    }

    fun updateInfo(command: UpdateCourseInfoCommand): Course {
        validate(command.userId)
        require(command.categories.isNotEmpty() && command.categories.isNotEmpty())

        return copy(
            content = command.content,
            places = updatePlaces(command.placeIds),
            categories = command.categories,
            visitDate = command.visitDate,
        )
    }

    fun delete(command: DeleteCourseCommand): Course {
        validate(command.userId)

        return copy(
            status = Status.DELETED,
        )
    }

    private fun validate(userId: Long) {
        when {
            this.status == Status.DELETED -> throw NotExistsCourseException
            this.userId != userId -> throw InvalidCourseWriterException
        }
    }

    private fun updatePlaces(placeIds: List<Long>): List<CoursePlace> {
        val places = this.places.associateBy { it.placeId }

        return placeIds.mapIndexed { index, placeId ->
            places[placeId]?.replaceOrder(index + 1)
                ?: CoursePlace.create(order = index + 1, placeId = placeId)
        }
    }

    companion object {
        /**
         * 새로운 코스를 생성합니다.
         *
         * @param command  Course 엔티티 생성을 위한 Command 객체
         * @param identifier  생성된 Course 엔티티의 새로운 ID를 할당하는 메서드
         * @author JiHongKim98
         */
        fun create(
            command: CreateCourseCommand,
            identifier: (Course) -> Long,
        ): Course =
            Course(
                id = 0L,
                userId = command.userId,
                region = command.region,
                content = command.content,
                visitDate = command.visitDate,
                places = orderingPlaces(command.placeIds),
                categories = command.categories,
                status = Status.ACTIVE,
            ).let {
                it.copy(id = identifier.invoke(it))
            }

        private fun orderingPlaces(placeIds: List<Long>): List<CoursePlace> =
            placeIds.mapIndexed { index, placeId ->
                CoursePlace.create(
                    order = index + 1,
                    placeId = placeId,
                )
            }
    }
}
