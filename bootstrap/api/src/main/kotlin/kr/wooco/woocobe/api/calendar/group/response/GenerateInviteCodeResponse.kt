package kr.wooco.woocobe.api.calendar.group.response

data class GenerateInviteCodeResponse(
    val groupId: Long,
    val inviteCode: String,
)
