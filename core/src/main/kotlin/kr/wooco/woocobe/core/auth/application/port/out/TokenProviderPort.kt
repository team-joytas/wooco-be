package kr.wooco.woocobe.core.auth.application.port.out

interface TokenProviderPort {
    fun generateAccessToken(userId: Long): String

    fun generateRefreshToken(tokenId: String): String

    fun extractUserId(accessToken: String): Long

    fun extractTokenId(refreshToken: String): String
}
