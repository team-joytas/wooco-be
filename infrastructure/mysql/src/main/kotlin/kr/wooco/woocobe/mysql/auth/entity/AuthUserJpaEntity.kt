package kr.wooco.woocobe.mysql.auth.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.mysql.common.entity.BaseTimeEntity
import kr.wooco.woocobe.mysql.common.utils.Tsid

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
