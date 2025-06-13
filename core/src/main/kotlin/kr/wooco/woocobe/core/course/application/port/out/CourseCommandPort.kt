package kr.wooco.woocobe.core.course.application.port.out

import kr.wooco.woocobe.core.course.domain.entity.Course

interface CourseCommandPort {
    /**
     * Course 애그리거트를 영속화시킵니다.
     *
     * @return Long 영속화 후 채번된 코스 전역 식별자
     * @author JiHongKim98
     */
    fun saveCourse(course: Course): Long

    /**
     * Course 애그리거트(Command 객체)를 가져옵니다.
     *
     * @return 영속화된 Course 애그리거트
     * @author JiHongKim98
     */
    fun getByCourseId(courseId: Long): Course
}
