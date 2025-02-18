package kr.wooco.woocobe.mysql.notification.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.mysql.common.entity.BaseTimeEntity
import kr.wooco.woocobe.mysql.common.utils.Tsid

@Entity
@Table(name = "device_tokens")
class DeviceTokenJpaEntity(
    @Column(name = "token", unique = true)
    val token: String,
    @Column(name = "user_id")
    val userId: Long,
    @Column(name = "is_active")
    val isActive: Boolean,
    @Id @Tsid
    @Column(name = "device_token_id")
    override val id: Long = 0L,
) : BaseTimeEntity()
