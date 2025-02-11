package kr.wooco.woocobe.course.application.port.out

import kr.wooco.woocobe.course.domain.entity.InterestCourse

fun interface SaveInterestCoursePersistencePort {
    fun saveInterestCourse(interestCourse: InterestCourse): InterestCourse
}
