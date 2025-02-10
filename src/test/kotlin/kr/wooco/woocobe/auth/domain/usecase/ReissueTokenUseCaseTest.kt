// package kr.wooco.woocobe.auth.domain.usecase
//
// import io.kotest.core.spec.style.BehaviorSpec
// import kr.wooco.woocobe.auth.adapter.out.persistence.repository.AuthTokenRedisRepository
// import kr.wooco.woocobe.support.IntegrationTest
// import kr.wooco.woocobe.support.MysqlCleaner
// import kr.wooco.woocobe.support.RedisCleaner
// import org.springframework.data.redis.core.StringRedisTemplate
//
// @IntegrationTest
// class ReissueTokenUseCaseTest(
//    private val stringRedisTemplate: StringRedisTemplate,
//    private val reissueTokenUseCase: ReissueTokenUseCase,
//    private val authTokenRedisRepository: AuthTokenRedisRepository,
// ) : BehaviorSpec({
//    listeners(MysqlCleaner(), RedisCleaner())
//
// //    Given("유효한 input 값이 들어올 경우") {
// //        val validTokenId = 1234567890L
// //        val validRefreshToken = "eyJhbGciOiJIUzM4NCJ9" +
// //                ".eyJ0b2tlbl9pZCI6MTIzNDU2Nzg5MCwiZXhwIjo5OTk5OTk5OTk5fQ" +
// //                ".ksUhjX5K1edXRQwlDwA40i_oACT9CubMdeiqOX3XBzhyfbNh5DXISg2oiXKpJg6N"
// //
// //        val input = ReissueTokenInput(refreshToken = validRefreshToken)
// //
// //        When("토큰 식별자와 일치하는 인증 토큰이 존재할 때") {
// //            val authTokenEntity =
// //                AuthTokenRedisEntity(tokenId = validTokenId, userId = 9876543210L).run(authTokenRedisRepository::save)
// //
// //            val sut = reissueTokenUseCase.execute(input)
// //
// //            Then("정상적으로 새로운 토큰을 발급한다.") {
// //                sut.accessToken.shouldNotBeNull()
// //                sut.refreshToken.shouldNotBeNull()
// //            }
// //
// //            Then("기존 토큰이 삭제된다.") {
// //                val expectNull = authTokenRedisRepository.findById(authTokenEntity.tokenId)
// //                expectNull.shouldBeNull()
// //            }
// //
// //            Then("새로운 인증 토큰이 저장된다.") {
// //                val authTokenEntities = stringRedisTemplate.keys("*")
// //                authTokenEntities.size shouldBe 1
// //            }
// //        }
// //
// //        When("저장되지 않는 인증 토큰 식별자가 주어질 때") {
// //            Then("NotExistsTokenException 오류가 발생한다.") {
// //                shouldThrow<RuntimeException> {
// //                    reissueTokenUseCase.execute(input)
// //                }
// //            }
// //        }
// //    }
// })
