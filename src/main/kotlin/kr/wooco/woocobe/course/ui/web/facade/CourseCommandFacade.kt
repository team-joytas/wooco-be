package kr.wooco.woocobe.course.ui.web.facade

import kr.wooco.woocobe.course.domain.usecase.AddCourseUseCase
import kr.wooco.woocobe.course.domain.usecase.AddInterestCourseInput
import kr.wooco.woocobe.course.domain.usecase.AddInterestCourseUseCase
import kr.wooco.woocobe.course.domain.usecase.DeleteCourseInput
import kr.wooco.woocobe.course.domain.usecase.DeleteCourseUseCase
import kr.wooco.woocobe.course.domain.usecase.DeleteInterestCourseInput
import kr.wooco.woocobe.course.domain.usecase.DeleteInterestCourseUseCase
import kr.wooco.woocobe.course.domain.usecase.UpdateCourseUseCase
import kr.wooco.woocobe.course.ui.web.controller.request.CreateCourseRequest
import kr.wooco.woocobe.course.ui.web.controller.request.UpdateCourseRequest
import kr.wooco.woocobe.course.ui.web.controller.response.CreateCourseResponse
import org.springframework.stereotype.Service

@Service
class CourseCommandFacade(
    private val addCourseUseCase: AddCourseUseCase,
    private val addInterestCourseUseCase: AddInterestCourseUseCase,
    private val updateCourseUseCase: UpdateCourseUseCase,
    private val deleteCourseUseCase: DeleteCourseUseCase,
    private val deleteInterestCourseUseCase: DeleteInterestCourseUseCase,
) {
    fun createCourse(
        userId: Long,
        request: CreateCourseRequest,
    ): CreateCourseResponse {
        val addCourseResult = addCourseUseCase.execute(request.toCommand(userId))
        return CreateCourseResponse(
            courseId = addCourseResult.courseId,
        )
    }

    fun addInterestCourse(
        userId: Long,
        courseId: Long,
    ) = addInterestCourseUseCase.execute(AddInterestCourseInput(userId = userId, courseId = courseId))

    fun updateCourse(
        userId: Long,
        courseId: Long,
        request: UpdateCourseRequest,
    ) = updateCourseUseCase.execute(request.toCommand(userId = userId, courseId = courseId))

    fun deleteCourse(
        userId: Long,
        courseId: Long,
    ) = deleteCourseUseCase.execute(DeleteCourseInput(userId = userId, courseId = courseId))

    fun deleteInterestCourse(
        userId: Long,
        courseId: Long,
    ) = deleteInterestCourseUseCase.execute(DeleteInterestCourseInput(userId = userId, courseId = courseId))
}
