package kr.wooco.woocobe.mysql.course.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.mysql.common.entity.BaseEntity
import kr.wooco.woocobe.mysql.common.utils.Tsid

@Entity
@Table(name = "course_places")
class CoursePlaceJpaEntity(
    @Column(name = "course_place_order")
    val order: Int,
    @Column(name = "course_id")
    val courseId: Long,
    @Column(name = "place_id")
    val placeId: Long,
    @Id @Tsid
    @Column(name = "course_place_id")
    override val id: Long = 0L,
) : BaseEntity()
