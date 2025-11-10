package kr.wooco.woocobe.core.group.domain.vo

import java.util.UUID

// TODO: 암호화 로직 필요
data class GroupInviteCode(
    val value: String,
) {

    companion object {
        private const val CODE_LENGTH = 16

        fun generate(): GroupInviteCode =
            GroupInviteCode(
                value = UUID.randomUUID().toString().replace("-", "").take(CODE_LENGTH)
            )
    }
}
