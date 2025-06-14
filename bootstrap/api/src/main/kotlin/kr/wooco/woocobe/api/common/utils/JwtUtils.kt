package kr.wooco.woocobe.api.common.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.ConfigurationProperties
import java.security.Key
import java.time.Instant
import java.util.Date

object JwtUtils {
    private const val USER_ID = "user_id"
    private const val TOKEN_ID = "token_id"

    private const val ACCESS_TOKEN_SUBJECT = "access"
    private const val REFRESH_TOKEN_SUBJECT = "refresh"

    private val properties: JwtProperties by lazy {
        SpringContextLoader.getBean(JwtProperties::class.java)
    }
    private val secretKey: Key = Keys.hmacShaKeyFor(properties.signingKey.toByteArray())
    private val jwtParser: JwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build()

    /**
     * JWT 클레임 안에 사용자 식별자가 추가된 엑세스 토큰을 생성합니다.
     *
     * @param userId 클레임 내 포함될 사용자 식별자
     * @author JiHongKim98
     * @return accessToken
     */
    fun createAccessToken(userId: Long): String =
        Jwts
            .builder()
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .setSubject(ACCESS_TOKEN_SUBJECT)
            .addClaims(mapOf(USER_ID to userId.toString()))
            .setExpiration(Date.from(Instant.now().plusSeconds(properties.accessExpireIn)))
            .compact()

    /**
     * JWT 클레임 안에 사용자 식별자와 토큰 식별자가 추가된 리프레쉬 토큰을 생성합니다.
     *
     * @param userId 클레임 내 포함될 사용자 식별자
     * @param tokenId 클레임 내 포함될 토큰 식별자
     * @author JiHongKim98
     * @return accessToken
     */
    fun createRefreshToken(
        userId: Long,
        tokenId: Long,
    ): String =
        Jwts
            .builder()
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .setSubject(REFRESH_TOKEN_SUBJECT)
            .addClaims(
                mapOf(
                    USER_ID to userId.toString(),
                    TOKEN_ID to tokenId.toString(),
                ),
            ).setExpiration(Date.from(Instant.now().plusSeconds(properties.refreshExpireIn)))
            .compact()

    /**
     * 엑세스 토큰안에서 user_id 값을 추출합니다.
     *
     * @author JiHongKim98
     * @return userId
     */
    fun extractAccessToken(accessToken: String): Long {
        val claims = extractClaims(accessToken)
        require(claims.subject == ACCESS_TOKEN_SUBJECT)

        return claims[USER_ID].toString().toLongOrNull().let {
            requireNotNull(it)
        }
    }

    /**
     * 리프레쉬 토큰안에서 user_id 값과 token_id 값을 추출합니다.
     *
     * @author JiHongKim98
     * @return Pair - 첫번째 값 userId, 두번째 값 tokenId
     */
    fun extractRefreshToken(refreshToken: String): Pair<Long, Long> {
        val claims = extractClaims(refreshToken)
        require(claims.subject == REFRESH_TOKEN_SUBJECT)

        val userId = claims[USER_ID].toString().toLongOrNull().let {
            requireNotNull(it)
        }
        val tokenId = claims[TOKEN_ID].toString().toLongOrNull().let {
            requireNotNull(it)
        }
        return Pair(userId, tokenId)
    }

    private fun extractClaims(token: String): Claims =
        try {
            jwtParser.parseClaimsJws(token).body
        } catch (e: Exception) {
            when (e) {
                is ExpiredJwtException -> throw JwtException.ExpiredTokenException
                else -> throw JwtException.InvalidTokenException
            }
        }

    @ConfigurationProperties(prefix = "app.jwt")
    private data class JwtProperties(
        val signingKey: String,
        val accessExpireIn: Long,
        val refreshExpireIn: Long,
    )

    sealed class JwtException(
        override val message: String,
    ) : RuntimeException(message) {
        data object ExpiredTokenException : JwtException("만료된 토큰입니다.") {
            private fun readResolve(): Any = ExpiredTokenException
        }

        data object InvalidTokenException : JwtException("유효하지 않는 토큰입니다.") {
            private fun readResolve(): Any = InvalidTokenException
        }
    }
}
