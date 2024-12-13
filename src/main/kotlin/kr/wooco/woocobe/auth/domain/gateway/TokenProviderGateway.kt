package kr.wooco.woocobe.auth.domain.gateway

interface TokenProviderGateway {
    fun generateAccessToken(userId: Long): String

    fun generateRefreshToken(tokenId: Long): String

    fun extractAccessToken(accessToken: String): Long

    fun extractRefreshToken(refreshToken: String): Long
}
