package kr.wooco.woocobe.course.ui.web.facade

import kr.wooco.woocobe.course.domain.usecase.CheckInterestCourseInput
import kr.wooco.woocobe.course.domain.usecase.CheckInterestCourseUseCase
import kr.wooco.woocobe.course.domain.usecase.GetAllByCourseInput
import kr.wooco.woocobe.course.domain.usecase.GetAllCourseInput
import kr.wooco.woocobe.course.domain.usecase.GetAllCourseUseCase
import kr.wooco.woocobe.course.domain.usecase.GetAllInterestCourseIdInput
import kr.wooco.woocobe.course.domain.usecase.GetAllInterestCourseIdUseCase
import kr.wooco.woocobe.course.domain.usecase.GetAllMyCourseUseCase
import kr.wooco.woocobe.course.domain.usecase.GetAllMyInterestCourseInput
import kr.wooco.woocobe.course.domain.usecase.GetAllMyInterestCourseUseCase
import kr.wooco.woocobe.course.domain.usecase.GetCourseInput
import kr.wooco.woocobe.course.domain.usecase.GetCourseUseCase
import kr.wooco.woocobe.course.ui.web.controller.response.CourseDetailResponse
import kr.wooco.woocobe.place.domain.usecase.GetAllPlaceInput
import kr.wooco.woocobe.place.domain.usecase.GetAllPlaceUseCase
import kr.wooco.woocobe.user.domain.usecase.GetAllUserInput
import kr.wooco.woocobe.user.domain.usecase.GetAllUserUseCase
import kr.wooco.woocobe.user.domain.usecase.GetUserInput
import kr.wooco.woocobe.user.domain.usecase.GetUserUseCase
import org.springframework.stereotype.Service

// TODO: 리팩토링

@Service
class CourseQueryFacade(
    private val getCourseUseCase: GetCourseUseCase,
    private val getAllCourseUseCase: GetAllCourseUseCase,
    private val getAllMyCourseUseCase: GetAllMyCourseUseCase,
    private val checkInterestCourseUseCase: CheckInterestCourseUseCase,
    private val getAllInterestCourseIdUseCase: GetAllInterestCourseIdUseCase,
    private val getAllMyInterestCourseUseCase: GetAllMyInterestCourseUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getAllUserUseCase: GetAllUserUseCase,
    private val getAllPlaceUseCase: GetAllPlaceUseCase,
) {
    fun getCourseDetail(
        userId: Long?,
        courseId: Long,
    ): CourseDetailResponse {
        val getCourseResult = getCourseUseCase.execute(GetCourseInput(courseId = courseId))

        val placeIds = getCourseResult.course.coursePlaces.map { it.placeId }
        val getAllPlaceResult = getAllPlaceUseCase.execute(GetAllPlaceInput(placeIds = placeIds))

        val getUserResult = getUserUseCase.execute(GetUserInput(userId = getCourseResult.course.userId))

        val checkInterestCourseResult = userId?.run {
            checkInterestCourseUseCase.execute(CheckInterestCourseInput(userId = userId, courseId = courseId))
        }

        return CourseDetailResponse.of(
            course = getCourseResult.course,
            places = getAllPlaceResult.places,
            user = getUserResult.user,
            isInterest = checkInterestCourseResult?.isInterested ?: false,
        )
    }

    fun getAllCourseDetail(
        userId: Long?,
        primaryRegion: String,
        secondaryRegion: String,
        category: String?,
        sort: String,
    ): List<CourseDetailResponse> {
        val getAllCourseResult = getAllCourseUseCase.execute(
            GetAllCourseInput(
                primaryRegion = primaryRegion,
                secondaryRegion = secondaryRegion,
                category = category,
                sort = sort,
            ),
        )

        val placeIds = getAllCourseResult.courses.flatMap { it.coursePlaces }.map { it.placeId }
        val getAllPlaceResult = getAllPlaceUseCase.execute(GetAllPlaceInput(placeIds = placeIds))

        val userIds = getAllCourseResult.courses.map { it.userId }
        val getAllUserResult = getAllUserUseCase.execute(GetAllUserInput(userIds = userIds))

        val courseIds = getAllCourseResult.courses.map { it.id }
        val getAllInterestCourseIdResult = userId?.run {
            getAllInterestCourseIdUseCase.execute(GetAllInterestCourseIdInput(userId = userId, courseIds = courseIds))
        }

        return CourseDetailResponse.listOf(
            courses = getAllCourseResult.courses,
            places = getAllPlaceResult.places,
            users = getAllUserResult.users,
            interestCourseIds = getAllInterestCourseIdResult?.interestCourseIds ?: emptyList(),
        )
    }

    fun getMyCourseDetail(
        userId: Long,
        sort: String,
    ): List<CourseDetailResponse> {
        val getAllMyCourseResult = getAllMyCourseUseCase.execute(GetAllByCourseInput(userId = userId, sort = sort))

        val getUserResult = getUserUseCase.execute(GetUserInput(userId = userId))

        val placeIds = getAllMyCourseResult.courses.flatMap { it.coursePlaces }.map { it.placeId }
        val getAllPlaceResult = getAllPlaceUseCase.execute(GetAllPlaceInput(placeIds = placeIds))

        val courseIds = getAllMyCourseResult.courses.map { it.id }
        val getAllInterestCourseIdResult =
            getAllInterestCourseIdUseCase.execute(GetAllInterestCourseIdInput(userId = userId, courseIds = courseIds))

        return CourseDetailResponse.listOf(
            courses = getAllMyCourseResult.courses,
            places = getAllPlaceResult.places,
            users = listOf(getUserResult.user),
            interestCourseIds = getAllInterestCourseIdResult.interestCourseIds,
        )
    }

    fun getAllMyInterestCourse(userId: Long): List<CourseDetailResponse> {
        val getAllMyInterestResult = getAllMyInterestCourseUseCase.execute(GetAllMyInterestCourseInput(userId = userId))

        val userIds = getAllMyInterestResult.courses.map { it.userId }
        val getAllUserResult = getAllUserUseCase.execute(GetAllUserInput(userIds = userIds))

        val placeIds = getAllMyInterestResult.courses.flatMap { it.coursePlaces }.map { it.placeId }
        val getAllPlaceResult = getAllPlaceUseCase.execute(GetAllPlaceInput(placeIds = placeIds))

        return CourseDetailResponse.listOf(
            courses = getAllMyInterestResult.courses,
            places = getAllPlaceResult.places,
            users = getAllUserResult.users,
            interestCourseIds = getAllMyInterestResult.courses.map { it.id },
        )
    }
}
