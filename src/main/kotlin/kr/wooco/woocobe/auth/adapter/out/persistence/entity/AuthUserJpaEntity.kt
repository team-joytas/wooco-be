package kr.wooco.woocobe.auth.adapter.out.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.common.infrastructure.storage.Tsid
import kr.wooco.woocobe.common.infrastructure.storage.entity.BaseTimeEntity

@Entity
@Table(name = "auth_users")
class AuthUserJpaEntity(
    @Column(name = "social_type")
    val socialType: String,
    @Column(name = "social_id")
    val socialId: String,
    @Column(name = "user_id")
    val userId: Long,
    @Id @Tsid
    @Column(name = "auth_user_id")
    override val id: Long,
) : BaseTimeEntity()
