package kr.wooco.woocobe.auth.infrastructure.token

import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date

@Component
class JWTProvider(
    @Value("\${app.jwt.signing-key}") private val signingKey: String,
    @Value("\${app.jwt.expiration.access-token}") private val accessTokenExpiration: Long,
    @Value("\${app.jwt.expiration.refresh-token}") private val refreshTokenExpiration: Long,
) {
    private val secretKey: Key = Keys.hmacShaKeyFor(signingKey.toByteArray())
    private val jwtParser: JwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build()

    fun generateAccessToken(authUserId: Long): String = createToken(USER_ID, authUserId, accessTokenExpiration)

    fun generateRefreshToken(tokenId: Long): String = createToken(TOKEN_ID, tokenId, refreshTokenExpiration)

    fun extractUserId(token: String): Long =
        runCatching {
            jwtParser
                .parseClaimsJws(token)
                .body[USER_ID]
                .toString()
                .toLong()
        }.getOrElse {
            throw RuntimeException()
        }

    fun extractTokenId(token: String): Long =
        runCatching {
            jwtParser
                .parseClaimsJws(token)
                .body[TOKEN_ID]
                .toString()
                .toLong()
        }.getOrElse {
            throw RuntimeException()
        }

    private fun createToken(
        key: String,
        value: Long,
        expiredAt: Long,
    ): String =
        Jwts
            .builder()
            .signWith(secretKey)
            .addClaims(mapOf(key to value.toString()))
            .setExpiration(Date(System.currentTimeMillis().plus(expiredAt)))
            .compact()

    companion object {
        private const val USER_ID = "user_id"
        private const val TOKEN_ID = "token_id"
    }
}
