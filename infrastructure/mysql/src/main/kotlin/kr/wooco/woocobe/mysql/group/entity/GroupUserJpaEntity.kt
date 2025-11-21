package kr.wooco.woocobe.mysql.group.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.core.group.domain.entity.Group
import kr.wooco.woocobe.mysql.common.entity.BaseTimeEntity
import kr.wooco.woocobe.mysql.common.utils.Tsid
import java.time.LocalDate

@Entity
@Table(name = "group_users")
data class GroupUserJpaEntity(
    @Column(name = "status")
    val status: String,
    @Column(name = "joined_at")
    val joinedAt: LocalDate,
    @Column(name = "role")
    val role: String,
    @Column(name = "user_id")
    val userId: Long,
    @Column(name = "group_id")
    val groupId: Long,
    @Id @Tsid
    @Column(name = "group_user_id")
    override val id: Long = 0L,
) : BaseTimeEntity() {
    companion object {
        fun listOf(
            group: Group,
            groupJpaEntity: GroupJpaEntity,
        ): List<GroupUserJpaEntity> =
            group.users.map { user ->
                GroupUserJpaEntity(
                    id = user.id,
                    groupId = groupJpaEntity.id,
                    userId = user.userId,
                    role = user.role.name,
                    joinedAt = user.joinedAt.value,
                    status = user.status.name,
                )
        }
    }
}
