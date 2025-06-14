package kr.wooco.woocobe.mysql.course.repository

import kr.wooco.woocobe.mysql.course.entity.CourseJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

@Suppress("ktlint")
interface CourseJpaRepository : JpaRepository<CourseJpaEntity, Long>, CourseCustomRepository {
    fun countByUserId(userId: Long): Long

    @Modifying
    @Query("UPDATE CourseJpaEntity c SET c.commentCount = c.commentCount + 1 WHERE c.id = :courseId")
    fun increaseComments(courseId: Long)

    @Modifying
    @Query("UPDATE CourseJpaEntity c SET c.commentCount = c.commentCount - 1 WHERE c.id = :courseId")
    fun decreaseComments(courseId: Long)

    @Modifying
    @Query("UPDATE CourseJpaEntity c SET c.likeCount = c.likeCount + 1 WHERE c.id = :courseId")
    fun increaseLikes(courseId: Long)

    @Modifying
    @Query("UPDATE CourseJpaEntity c SET c.likeCount = c.likeCount - 1 WHERE c.id = :courseId")
    fun decreaseLikes(courseId: Long)
}
