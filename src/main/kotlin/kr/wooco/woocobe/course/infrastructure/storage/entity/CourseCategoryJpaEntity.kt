package kr.wooco.woocobe.course.infrastructure.storage.entity

import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.common.infrastructure.storage.BaseTimeEntity

@Entity
@Table(name = "course_categories")
class CourseCategoryJpaEntity(
    @Column(name = "category_name")
    val name: String,
    @Column(name = "course_id")
    val courseId: Long,
    @Id @Tsid
    @Column(name = "course_category_id")
    val id: Long? = 0L,
) : BaseTimeEntity()
