package kr.wooco.woocobe.api.group

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kr.wooco.woocobe.api.group.request.CreateGroupRequest
import kr.wooco.woocobe.api.group.request.JoinGroupRequest
import kr.wooco.woocobe.api.group.request.UpdateGroupInfoRequest
import kr.wooco.woocobe.api.group.response.CreateGroupResponse
import kr.wooco.woocobe.api.group.response.GenerateInviteCodeResponse
import kr.wooco.woocobe.api.group.response.GroupDetailResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "그룹 API")
interface GroupApi {
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "그룹 생성", description = "새로운 그룹을 생성합니다.")
    fun createGroup(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: CreateGroupRequest,
    ): ResponseEntity<CreateGroupResponse>

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "그룹 정보 수정", description = "그룹 정보를 수정합니다.")
    fun updateGroupInfo(
        @AuthenticationPrincipal userId: Long,
        @PathVariable groupId: Long,
        @RequestBody request: UpdateGroupInfoRequest,
    ): ResponseEntity<Unit>

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "그룹 가입", description = "초대코드를 통해 그룹에 가입합니다.")
    fun joinGroup(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: JoinGroupRequest,
    ): ResponseEntity<Unit>

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "그룹 탈퇴", description = "그룹을 탈퇴합니다.")
    fun leaveGroup(
        @AuthenticationPrincipal userId: Long,
        @PathVariable groupId: Long,
    ): ResponseEntity<Unit>

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "그룹 삭제", description = "그룹을 삭제합니다.")
    fun deleteGroup(
        @AuthenticationPrincipal userId: Long,
        @PathVariable groupId: Long,
    ): ResponseEntity<Unit>

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "그룹 유저 방출", description = "그룹 유저를 방출합니다.")
    fun expelGroupUser(
        @AuthenticationPrincipal userId: Long,
        @PathVariable groupId: Long,
        @PathVariable targetId: Long,
    ): ResponseEntity<Unit>

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "그룹 초대코드 생성", description = "그룹 초대코드를 생성합니다.")
    fun generateInviteCode(
        @AuthenticationPrincipal userId: Long,
        @PathVariable groupId: Long,
    ): ResponseEntity<GenerateInviteCodeResponse>

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "그룹 상세 조회", description = "그룹 상세 정보를 조회합니다.")
    fun readGroup(
        @AuthenticationPrincipal userId: Long,
        @PathVariable groupId: Long,
    ): ResponseEntity<GroupDetailResponse>

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "그룹 목록 조회", description = "자신이 속한 그룹 목록 정보를 조회합니다.")
    fun readAllGroup(
        @AuthenticationPrincipal userId: Long,
    ): ResponseEntity<List<GroupDetailResponse>>
}
