package kr.wooco.woocobe.mysql.group.repository

import kr.wooco.woocobe.mysql.group.entity.GroupJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface GroupJpaRepository : JpaRepository<GroupJpaEntity, Long> {
    fun findByIdAndStatus(
        id: Long,
        status: String,
    ): GroupJpaEntity?

    fun findAllByIdInAndStatus(
        groupIds: List<Long>,
        status: String,
    ): List<GroupJpaEntity>

    fun findByInviteCode(inviteCode: String): GroupJpaEntity?
}
