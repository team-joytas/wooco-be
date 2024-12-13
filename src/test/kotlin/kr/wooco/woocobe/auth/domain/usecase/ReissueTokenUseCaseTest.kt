package kr.wooco.woocobe.auth.domain.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kr.wooco.woocobe.auth.domain.gateway.AuthTokenStorageGateway
import kr.wooco.woocobe.auth.domain.model.AuthToken
import kr.wooco.woocobe.support.IntegrationTest
import kr.wooco.woocobe.support.MysqlCleaner
import kr.wooco.woocobe.support.RedisCleaner
import org.springframework.data.redis.core.StringRedisTemplate

@IntegrationTest
class ReissueTokenUseCaseTest(
    private val stringRedisTemplate: StringRedisTemplate,
    private val reissueTokenUseCase: ReissueTokenUseCase,
    private val authTokenStorageGateway: AuthTokenStorageGateway,
) : BehaviorSpec({
    listeners(MysqlCleaner(), RedisCleaner())

    Given("유효한 input 값이 들어올 때") {
        val validTokenId = 1234567890L
        val validRefreshToken = "eyJhbGciOiJIUzM4NCJ9" +
                ".eyJ0b2tlbl9pZCI6MTIzNDU2Nzg5MCwiZXhwIjo5OTk5OTk5OTk5fQ" +
                ".ksUhjX5K1edXRQwlDwA40i_oACT9CubMdeiqOX3XBzhyfbNh5DXISg2oiXKpJg6N"

        val input = ReissueTokenInput(refreshToken = validRefreshToken)

        When("토큰 식별자와 일치하는 인증 토큰이 존재하는 경우") {
            AuthToken(userId = 9876543210L, tokenId = validTokenId).let {
                authTokenStorageGateway.save(it)
            }

            val sut = reissueTokenUseCase.execute(input)

            Then("정상적으로 새로운 토큰을 발급한다.") {
                sut.accessToken.shouldNotBeNull()
                sut.refreshToken.shouldNotBeNull()
            }

            Then("인증 토큰의 식별자는 변경되어 저장된다.") {
                val tokens = stringRedisTemplate.keys("token:*")
                tokens.size shouldBe 1

                val expectNull = stringRedisTemplate.opsForValue().get(validTokenId.toString())
                expectNull.shouldBeNull()
            }
        }

        When("저장되지 않는 인증 토큰 식별자가 주어진 경우") {
            Then("NotExistsTokenException 오류가 발생한다.") {
                shouldThrow<RuntimeException> {
                    reissueTokenUseCase.execute(input)
                }
            }
        }
    }
})
