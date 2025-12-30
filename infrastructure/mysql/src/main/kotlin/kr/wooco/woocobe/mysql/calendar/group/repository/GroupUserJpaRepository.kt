package kr.wooco.woocobe.mysql.calendar.group.repository

import kr.wooco.woocobe.mysql.calendar.group.entity.GroupUserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface GroupUserJpaRepository : JpaRepository<GroupUserJpaEntity, Long> {

    fun findAllByGroupIdAndStatus(groupId: Long, status: String): List<GroupUserJpaEntity>

    fun findAllByUserIdAndStatus(userId: Long, status: String): List<GroupUserJpaEntity>

    fun deleteAllInBatchByGroupId(groupId: Long)

    fun findAllByGroupIdIn(groupIds: List<Long>): List<GroupUserJpaEntity>
}
