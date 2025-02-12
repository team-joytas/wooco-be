package kr.wooco.woocobe.mysql.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.mysql.common.entity.BaseTimeEntity
import kr.wooco.woocobe.mysql.common.utils.Tsid

@Entity
@Table(name = "users")
class UserJpaEntity(
    @Column(name = "status")
    val status: String,
    @Column(name = "description")
    val description: String,
    @Column(name = "profile_url")
    val profileUrl: String,
    @Column(name = "name")
    val name: String,
    @Id @Tsid
    @Column(name = "user_id")
    override val id: Long = 0L,
) : BaseTimeEntity()
