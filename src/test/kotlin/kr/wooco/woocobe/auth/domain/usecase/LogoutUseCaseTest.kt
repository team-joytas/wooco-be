package kr.wooco.woocobe.auth.domain.usecase

import io.kotest.core.spec.style.BehaviorSpec
import kr.wooco.woocobe.auth.infrastructure.cache.repository.AuthTokenRedisRepository
import kr.wooco.woocobe.support.IntegrationTest
import kr.wooco.woocobe.support.MysqlCleaner
import kr.wooco.woocobe.support.RedisCleaner

@IntegrationTest
class LogoutUseCaseTest(
    private val logoutUseCase: LogoutUseCase,
    private val authTokenRedisRepository: AuthTokenRedisRepository,
) : BehaviorSpec({
    listeners(MysqlCleaner(), RedisCleaner())

//    Given("유효한 input 값이 들어올 경우") {
//        val validUserId = 9876543210L
//        val validTokenId = 1234567890L
//        val validRefreshToken = "eyJhbGciOiJIUzM4NCJ9" +
//                ".eyJ0b2tlbl9pZCI6MTIzNDU2Nzg5MCwiZXhwIjo5OTk5OTk5OTk5fQ" +
//                ".ksUhjX5K1edXRQwlDwA40i_oACT9CubMdeiqOX3XBzhyfbNh5DXISg2oiXKpJg6N"
//
//        val input = LogoutInput(userId = validUserId, refreshToken = validRefreshToken)
//
//        When("토큰 식별자와 일치하는 인증 토큰이 존재할 때") {
//            val authTokenEntity =
//                AuthTokenRedisEntity(tokenId = validTokenId, userId = validUserId).run(authTokenRedisRepository::save)
//
//            logoutUseCase.execute(input)
//
//            Then("정상적으로 인증 토큰을 삭제한다.") {
//                val expectNull = authTokenRedisRepository.findById(authTokenEntity.tokenId)
//                expectNull.shouldBeNull()
//            }
//        }
//
//        When("토큰 식별자와 일치하는 인증 토큰이 존재하지 않을 때") {
//            Then("NotExistsAuthTokenException 오류가 발생한다.") {
//                shouldThrow<RuntimeException> {
//                    logoutUseCase.execute(input)
//                }
//            }
//        }
//
//        When("사용자 식별자와 일치하지 않는 인증 토큰일 때") {
//            AuthTokenRedisEntity(tokenId = validTokenId, userId = 999999999L).run(authTokenRedisRepository::save)
//
//            Then("UnMatchedTokenException 오류가 발생한다.") {
//                shouldThrow<RuntimeException> {
//                    logoutUseCase.execute(input)
//                }
//            }
//        }
//    }
})
