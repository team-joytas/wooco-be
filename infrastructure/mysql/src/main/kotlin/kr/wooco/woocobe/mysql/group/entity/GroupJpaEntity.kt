package kr.wooco.woocobe.mysql.group.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.mysql.common.entity.BaseTimeEntity
import kr.wooco.woocobe.mysql.common.utils.Tsid

@Entity
@Table(name = "`groups`")
data class GroupJpaEntity(
    @Column(name = "group_status")
    val status: String,
    @Column(name = "invite_code")
    val inviteCode: String,
    @Column(name = "type")
    val type: String,
    @Column(name = "name")
    val name: String,
    @Column(name = "owner_id")
    val ownerId: Long,
    @Id @Tsid
    @Column(name = "group_id")
    override val id: Long = 0L,
) : BaseTimeEntity()
