package kr.wooco.woocobe.core.course.application.port.out

import kr.wooco.woocobe.core.course.domain.entity.InterestCourse

fun interface SaveInterestCoursePersistencePort {
    fun saveInterestCourse(interestCourse: InterestCourse): InterestCourse
}
