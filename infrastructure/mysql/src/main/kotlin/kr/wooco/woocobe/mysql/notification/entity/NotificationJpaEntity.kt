package kr.wooco.woocobe.mysql.notification.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.mysql.common.entity.BaseTimeEntity
import kr.wooco.woocobe.mysql.common.utils.Tsid

@Entity
@Table(name = "notifications")
class NotificationJpaEntity(
    @Column(name = "user_id")
    val userId: Long,
    @Column(name = "target_id")
    val targetId: Long,
    @Column(name = "target_name")
    val targetName: String,
    @Column(name = "type")
    val type: String,
    @Column(name = "status")
    val status: String,
    @Column(name = "read_status")
    val readStatus: String,
    @Id @Tsid
    @Column(name = "notification_id")
    override val id: Long = 0L,
) : BaseTimeEntity()
