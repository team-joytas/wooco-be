package kr.wooco.woocobe.auth.domain.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldBeNull
import kr.wooco.woocobe.auth.domain.gateway.AuthTokenStorageGateway
import kr.wooco.woocobe.auth.domain.model.AuthToken
import kr.wooco.woocobe.support.IntegrationTest
import kr.wooco.woocobe.support.MysqlCleaner
import kr.wooco.woocobe.support.RedisCleaner
import org.springframework.data.redis.core.StringRedisTemplate

@IntegrationTest
class LogoutUseCaseTest(
    private val logoutUseCase: LogoutUseCase,
    private val stringRedisTemplate: StringRedisTemplate,
    private val authTokenStorageGateway: AuthTokenStorageGateway,
) : BehaviorSpec({
    listeners(MysqlCleaner(), RedisCleaner())

    Given("유효한 input 값이 들어올 때") {
        val validUserId = 9876543210L
        val validTokenId = 1234567890L
        val validRefreshToken = "eyJhbGciOiJIUzM4NCJ9" +
                ".eyJ0b2tlbl9pZCI6MTIzNDU2Nzg5MCwiZXhwIjo5OTk5OTk5OTk5fQ" +
                ".ksUhjX5K1edXRQwlDwA40i_oACT9CubMdeiqOX3XBzhyfbNh5DXISg2oiXKpJg6N"

        val input = LogoutInput(userId = validUserId, refreshToken = validRefreshToken)

        When("토큰 식별자와 일치하는 인증 토큰이 존재하는 경우") {
            val authToken =
                AuthToken(userId = validUserId, tokenId = validTokenId).let {
                    authTokenStorageGateway.save(it)
                }

            logoutUseCase.execute(input)

            Then("정상적으로 인증 토큰을 삭제한다.") {
                val storageToken = stringRedisTemplate.opsForValue().get(authToken.tokenId.toString())
                storageToken.shouldBeNull()
            }
        }

        When("사용자 식별자와 일치하지 않는 인증 토큰인 경우") {
            AuthToken(userId = 0L, tokenId = validTokenId).let {
                authTokenStorageGateway.save(it)
            }

            Then("UnMatchedTokenException 오류가 발생한다.") {
                shouldThrow<RuntimeException> {
                    logoutUseCase.execute(input)
                }
            }
        }

        When("토큰 식별자와 일치하는 인증 토큰이 존재하지 않는 경우") {
            Then("NotExistsTokenException 오류가 발생한다.") {
                shouldThrow<RuntimeException> {
                    logoutUseCase.execute(input)
                }
            }
        }
    }
})
