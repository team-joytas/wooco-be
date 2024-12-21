package kr.wooco.woocobe.auth.infrastructure.gateway

import kr.wooco.woocobe.auth.domain.gateway.TokenProviderGateway
import kr.wooco.woocobe.auth.infrastructure.token.JWTProvider
import org.springframework.stereotype.Component

@Component
class JwtTokenProviderGateway(
    private val jwtProvider: JWTProvider,
) : TokenProviderGateway {
    override fun generateAccessToken(userId: Long): String = jwtProvider.generateAccessToken(userId)

    override fun generateRefreshToken(tokenId: Long): String = jwtProvider.generateRefreshToken(tokenId)

    override fun extractUserId(accessToken: String): Long = jwtProvider.extractUserId(accessToken)

    override fun extractTokenId(refreshToken: String): Long = jwtProvider.extractTokenId(refreshToken)
}
