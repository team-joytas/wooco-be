package kr.wooco.woocobe.api.group

import kr.wooco.woocobe.api.group.request.CreateGroupRequest
import kr.wooco.woocobe.api.group.request.JoinGroupRequest
import kr.wooco.woocobe.api.group.request.UpdateGroupInfoRequest
import kr.wooco.woocobe.api.group.response.CreateGroupResponse
import kr.wooco.woocobe.api.group.response.GenerateInviteCodeResponse
import kr.wooco.woocobe.api.group.response.GroupDetailResponse
import kr.wooco.woocobe.core.group.application.port.`in`.CreateGroupUseCase
import kr.wooco.woocobe.core.group.application.port.`in`.DeleteGroupUseCase
import kr.wooco.woocobe.core.group.application.port.`in`.ExpelGroupUserUseCase
import kr.wooco.woocobe.core.group.application.port.`in`.GenerateInviteCodeUseCase
import kr.wooco.woocobe.core.group.application.port.`in`.JoinGroupUseCase
import kr.wooco.woocobe.core.group.application.port.`in`.LeaveGroupUseCase
import kr.wooco.woocobe.core.group.application.port.`in`.ReadAllGroupUseCase
import kr.wooco.woocobe.core.group.application.port.`in`.ReadGroupUseCase
import kr.wooco.woocobe.core.group.application.port.`in`.UpdateGroupInfoUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/groups")
class GroupController(
    private val createGroupUseCase: CreateGroupUseCase,
    private val updateGroupInfoUseCase: UpdateGroupInfoUseCase,
    private val joinGroupUseCase: JoinGroupUseCase,
    private val leaveGroupUseCase: LeaveGroupUseCase,
    private val deleteGroupUseCase: DeleteGroupUseCase,
    private val generateInviteCodeUseCase: GenerateInviteCodeUseCase,
    private val expelGroupUserUseCase: ExpelGroupUserUseCase,
    private val readGroupUseCase: ReadGroupUseCase,
    private val readAllGroupUseCase: ReadAllGroupUseCase,
) : GroupApi {
    @PostMapping
    override fun createGroup(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: CreateGroupRequest,
    ): ResponseEntity<CreateGroupResponse> {
        val command = request.toCommand(userId)
        val results = createGroupUseCase.createGroup(command)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(CreateGroupResponse(results))
    }

    @PatchMapping("/{groupId}")
    override fun updateGroupInfo(
        @AuthenticationPrincipal userId: Long,
        @PathVariable groupId: Long,
        @RequestBody request: UpdateGroupInfoRequest,
    ): ResponseEntity<Unit> {
        val command = request.toCommand(userId = userId, groupId = groupId)
        updateGroupInfoUseCase.updateGroupInfo(command)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{groupId}")
    override fun deleteGroup(
        @AuthenticationPrincipal userId: Long,
        @PathVariable groupId: Long,
    ): ResponseEntity<Unit> {
        val command = DeleteGroupUseCase.Command(userId = userId, groupId = groupId)
        deleteGroupUseCase.deleteGroup(command)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/{groupId}/invite-code")
    override fun generateInviteCode(
        @AuthenticationPrincipal userId: Long,
        @PathVariable groupId: Long,
    ): ResponseEntity<GenerateInviteCodeResponse> {
        val command = GenerateInviteCodeUseCase.Command(userId = userId, groupId = groupId)
        val results = generateInviteCodeUseCase.generateInviteCode(command)
        return ResponseEntity.ok().body(GenerateInviteCodeResponse(groupId = groupId, inviteCode = results))
    }

    @PostMapping("/users")
    override fun joinGroup(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: JoinGroupRequest,
    ): ResponseEntity<Unit> {
        val command = request.toCommand(userId)
        joinGroupUseCase.joinGroup(command)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{groupId}/users/me")
    override fun leaveGroup(
        @AuthenticationPrincipal userId: Long,
        @PathVariable groupId: Long,
    ): ResponseEntity<Unit> {
        val command = LeaveGroupUseCase.Command(userId = userId, groupId = groupId)
        leaveGroupUseCase.leaveGroup(command)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{groupId}/users/{targetId}")
    override fun expelGroupUser(
        @AuthenticationPrincipal userId: Long,
        @PathVariable groupId: Long,
        @PathVariable targetId: Long,
    ): ResponseEntity<Unit> {
        val command = ExpelGroupUserUseCase.Command(userId = userId, groupId = groupId, targetId = targetId)
        expelGroupUserUseCase.expelGroupUser(command)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{groupId}")
    override fun readGroup(
        @AuthenticationPrincipal userId: Long,
        @PathVariable groupId: Long,
    ): ResponseEntity<GroupDetailResponse> {
        val query = ReadGroupUseCase.Query(userId = userId, groupId = groupId)
        val result = readGroupUseCase.readGroup(query)
        return ResponseEntity.ok().body(GroupDetailResponse.from(result))
    }

    @GetMapping
    override fun readAllGroup(
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<List<GroupDetailResponse>> {
        val query = ReadAllGroupUseCase.Query(userId = userId)
        val results = readAllGroupUseCase.readAllGroup(query)
        return ResponseEntity.ok().body(GroupDetailResponse.listFrom(results))
    }
}
