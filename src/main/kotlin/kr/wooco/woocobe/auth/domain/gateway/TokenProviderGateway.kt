package kr.wooco.woocobe.auth.domain.gateway

interface TokenProviderGateway {
    fun generateAccessToken(userId: Long): String

    fun generateRefreshToken(tokenId: Long): String

    fun extractUserId(accessToken: String): Long

    fun extractTokenId(refreshToken: String): Long
}
