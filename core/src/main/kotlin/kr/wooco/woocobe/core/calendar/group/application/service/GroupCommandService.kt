package kr.wooco.woocobe.core.calendar.group.application.service

import kr.wooco.woocobe.core.calendar.group.application.port.`in`.CreateGroupUseCase
import kr.wooco.woocobe.core.calendar.group.application.port.`in`.DeleteGroupUseCase
import kr.wooco.woocobe.core.calendar.group.application.port.`in`.ExpelGroupUserUseCase
import kr.wooco.woocobe.core.calendar.group.application.port.`in`.GenerateInviteCodeUseCase
import kr.wooco.woocobe.core.calendar.group.application.port.`in`.JoinGroupUseCase
import kr.wooco.woocobe.core.calendar.group.application.port.`in`.LeaveGroupUseCase
import kr.wooco.woocobe.core.calendar.group.application.port.`in`.UpdateGroupInfoUseCase
import kr.wooco.woocobe.core.calendar.group.application.port.out.GroupCommandPort
import kr.wooco.woocobe.core.calendar.group.domain.entity.Group
import org.springframework.stereotype.Service

@Service
class GroupCommandService(
    private val groupCommandPort: GroupCommandPort,
) : CreateGroupUseCase,
    DeleteGroupUseCase,
    JoinGroupUseCase,
    LeaveGroupUseCase,
    UpdateGroupInfoUseCase,
    ExpelGroupUserUseCase,
    GenerateInviteCodeUseCase {
    override fun createGroup(command: CreateGroupUseCase.Command): Long {
        val group = Group.Companion.create(command.toCreateCommand()) { groupCommandPort.save(it) }
        return group.id
    }

    override fun deleteGroup(command: DeleteGroupUseCase.Command): Long {
        val group = groupCommandPort.getById(command.groupId)
        val deleted = group.delete(command.toDeleteCommand())
        groupCommandPort.save(deleted)
        return deleted.id
    }

    override fun updateGroupInfo(command: UpdateGroupInfoUseCase.Command): Long {
        val group = groupCommandPort.getById(command.groupId)
        val updated = group.updateInfo(command.toUpdateGroupInfoCommand())
        groupCommandPort.save(updated)
        return updated.id
    }

    override fun joinGroup(command: JoinGroupUseCase.Command): Long {
        val group = groupCommandPort.getByInviteCode(command.inviteCode)
        val updated = group.join(command.toJoinCommand())
        groupCommandPort.save(updated)
        return updated.id
    }

    override fun leaveGroup(command: LeaveGroupUseCase.Command): Long {
        val group = groupCommandPort.getById(command.groupId)
        val updated = group.leave(command.toLeaveCommand())
        groupCommandPort.save(updated)
        return updated.id
    }

    override fun expelGroupUser(command: ExpelGroupUserUseCase.Command): Long {
        val group = groupCommandPort.getById(command.groupId)
        val updated = group.expel(command.toExpelGroupUserCommand())
        groupCommandPort.save(updated)
        return updated.id
    }

    override fun generateInviteCode(command: GenerateInviteCodeUseCase.Command): String {
        val group = groupCommandPort.getById(command.groupId)
        val updated = group.generateInviteCode(command.toGenerateInviteCodeCommand())
        groupCommandPort.save(updated)
        return updated.inviteCode.value
    }
}
