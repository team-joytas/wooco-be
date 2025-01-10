package kr.wooco.woocobe.user.infrastructure.storage.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.common.infrastructure.storage.Tsid
import kr.wooco.woocobe.common.infrastructure.storage.entity.BaseTimeEntity

@Entity
@Table(name = "users")
class UserJpaEntity(
    @Column(name = "profile_url")
    val profileUrl: String,
    @Column(name = "name")
    val name: String,
    @Id @Tsid
    @Column(name = "user_id")
    override val id: Long = 0L,
) : BaseTimeEntity()
