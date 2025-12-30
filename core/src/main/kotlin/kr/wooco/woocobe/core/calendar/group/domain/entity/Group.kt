package kr.wooco.woocobe.core.calendar.group.domain.entity

import kr.wooco.woocobe.core.calendar.group.domain.command.CreateGroupCommand
import kr.wooco.woocobe.core.calendar.group.domain.command.DeleteGroupCommand
import kr.wooco.woocobe.core.calendar.group.domain.command.ExpelGroupUserCommand
import kr.wooco.woocobe.core.calendar.group.domain.command.GenerateInviteCodeCommand
import kr.wooco.woocobe.core.calendar.group.domain.command.JoinGroupCommand
import kr.wooco.woocobe.core.calendar.group.domain.command.LeaveGroupCommand
import kr.wooco.woocobe.core.calendar.group.domain.command.UpdateGroupInfoCommand
import kr.wooco.woocobe.core.calendar.group.domain.exception.AlreadyGroupMemberException
import kr.wooco.woocobe.core.calendar.group.domain.exception.GroupAlreadyDeletedException
import kr.wooco.woocobe.core.calendar.group.domain.exception.GroupHasUserException
import kr.wooco.woocobe.core.calendar.group.domain.exception.GroupUserLimitExceededException
import kr.wooco.woocobe.core.calendar.group.domain.exception.InvalidGroupOperationException
import kr.wooco.woocobe.core.calendar.group.domain.exception.NotGroupMemberException
import kr.wooco.woocobe.core.calendar.group.domain.exception.NotGroupOwnerException
import kr.wooco.woocobe.core.calendar.group.domain.vo.GroupInviteCode
import kr.wooco.woocobe.core.common.domain.entity.AggregateRoot

data class Group(
    override val id: Long,
    val ownerId: Long,
    val name: Name,
    val inviteCode: GroupInviteCode,
    val users: List<GroupUser>,
    val status: Status,
) : AggregateRoot() {

    @JvmInline
    value class Name(
        val value: String,
    ) {
        init {
            require(value.isNotBlank()) { "그룹 이름은 빈 값일 수 없습니다." }
            require(value.length <= 30) { "그룹 이름은 최대 30자까지 가능합니다." }
        }
    }

    enum class Status { ACTIVE, DELETED }

    fun updateInfo(command: UpdateGroupInfoCommand): Group {
        requireActive()
        if (!isMember(command.userId)) throw NotGroupMemberException
        return copy(
            name = command.name
        )
    }

    fun delete(command: DeleteGroupCommand): Group {
        requireActive()
        requireOwner(command.userId)
        if (users.size > 1) throw GroupHasUserException
        return copy(
            status = Status.DELETED
        )
    }

    fun generateInviteCode(command: GenerateInviteCodeCommand): Group {
        requireActive()
        if (!isMember(command.userId)) throw NotGroupMemberException
        return copy(
            inviteCode = GroupInviteCode.Companion.generate()
        )
    }

    fun join(command: JoinGroupCommand): Group {
        requireActive()
        if (isMember(command.userId)) throw AlreadyGroupMemberException
        if (users.size >= MAX_USERS_SIZE) throw GroupUserLimitExceededException
        return copy(
            users = users + GroupUser.createMember(groupId = this.id, userId = command.userId)
        )
    }

    fun leave(command: LeaveGroupCommand): Group {
        requireActive()
        if (!isMember(command.userId)) throw NotGroupMemberException
        if (command.userId == ownerId && users.size > 1) throw InvalidGroupOperationException
        return copy(
            users = users.filterNot { it.userId == command.userId }
        )
    }

    fun expel(command: ExpelGroupUserCommand): Group {
        requireActive()
        requireOwner(command.userId)
        if (command.targetId == command.userId) throw InvalidGroupOperationException
        return copy(
            users = users.filterNot { it.userId == command.targetId }
        )
    }

    fun isMember(userId: Long): Boolean =
        users.any { it.userId == userId }

    private fun requireActive() {
        if (status != Status.ACTIVE) throw GroupAlreadyDeletedException
    }

    private fun requireOwner(userId: Long) {
        if (ownerId != userId) throw NotGroupOwnerException
    }

    companion object {
        private const val MAX_USERS_SIZE = 15

        /**
         * 새로운 그룹을 생성합니다.
         *
         * @param command Group 엔티티 생성을 위한 Command 객체
         * @param identifier 생성된 Group 엔티티의 새로운 ID를 할당하는 메서드
         * @author Junseoparkk
         */
        fun create(
            command: CreateGroupCommand,
            identifier: (Group) -> Long,
        ): Group {
            val group = Group(
                id = 0L,
                ownerId = command.userId,
                name = command.name,
                inviteCode = GroupInviteCode.Companion.generate(),
                users = listOf(GroupUser.createOwner(0L, command.userId)),
                status = Status.ACTIVE,
            )
            val id = identifier(group)
            val owner = group.users.first().copy(
                id = id,
            )
            return group.copy(
                id = id,
                users = listOf(owner)
            )
        }
    }
}
