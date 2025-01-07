package kr.wooco.woocobe.image.domain.model

import java.util.UUID

data class ImageKey(
    val key: String,
) {
    constructor(userId: Long) : this(
        key = "$userId$KEY_DELIMITER${generateRandomKey()}",
    )

    companion object {
        private const val KEY_DELIMITER = "/"

        private fun generateRandomKey(): String = UUID.randomUUID().toString()
    }
}
