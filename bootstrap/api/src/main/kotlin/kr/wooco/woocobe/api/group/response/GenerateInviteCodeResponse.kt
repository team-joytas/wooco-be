package kr.wooco.woocobe.api.group.response

data class GenerateInviteCodeResponse(
    val groupId: Long,
    val inviteCode: String,
)
