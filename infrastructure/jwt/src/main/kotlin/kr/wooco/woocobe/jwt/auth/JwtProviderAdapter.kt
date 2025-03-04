package kr.wooco.woocobe.jwt.auth

import kr.wooco.woocobe.core.auth.application.port.out.TokenProviderPort
import org.springframework.stereotype.Component

@Component
internal class JwtProviderAdapter(
    private val jwtProvider: JWTProvider,
) : TokenProviderPort {
    override fun generateAccessToken(userId: Long): String = jwtProvider.generateAccessToken(userId)

    override fun generateRefreshToken(tokenId: String): String = jwtProvider.generateRefreshToken(tokenId)

    override fun extractUserId(accessToken: String): Long = jwtProvider.extractUserId(accessToken)

    override fun extractTokenId(refreshToken: String): String = jwtProvider.extractTokenId(refreshToken)
}
