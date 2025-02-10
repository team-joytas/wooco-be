package kr.wooco.woocobe.auth.adapter.out.jwt

import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import kr.wooco.woocobe.auth.domain.exception.InvalidTokenException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date

@Component
internal class JWTProvider(
    @Value("\${app.jwt.signing-key}") private val signingKey: String,
    @Value("\${app.jwt.expiration.access-token}") private val accessTokenExpiration: Long,
    @Value("\${app.jwt.expiration.refresh-token}") private val refreshTokenExpiration: Long,
) {
    private val secretKey: Key = Keys.hmacShaKeyFor(signingKey.toByteArray())
    private val jwtParser: JwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build()

    fun generateAccessToken(userId: Long): String = createToken(USER_ID, userId, accessTokenExpiration)

    fun generateRefreshToken(tokenId: String): String = createToken(TOKEN_ID, tokenId, refreshTokenExpiration)

    fun extractUserId(token: String): Long = extractKey(USER_ID, token).toLong()

    fun extractTokenId(token: String): String = extractKey(TOKEN_ID, token)

    private fun extractKey(
        key: String,
        token: String,
    ): String =
        runCatching {
            jwtParser.parseClaimsJws(token).body[key].toString()
        }.getOrElse {
            throw InvalidTokenException
        }

    private fun createToken(
        key: String,
        value: Any,
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
