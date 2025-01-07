package kr.wooco.woocobe.plan.infrastructure.storage.entity

import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.common.infrastructure.storage.BaseTimeEntity

@Entity
@Table(name = "plan_categories")
class PlanCategoryJpaEntity(
    @Column(name = "plan_id")
    val planId: Long,
    @Column(name = "category_name")
    val name: String,
    @Id @Tsid
    @Column(name = "plan_category_id")
    val id: Long? = 0L,
) : BaseTimeEntity()