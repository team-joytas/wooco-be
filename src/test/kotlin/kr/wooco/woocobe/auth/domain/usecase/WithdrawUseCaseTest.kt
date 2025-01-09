package kr.wooco.woocobe.auth.domain.usecase

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldBeNull
import kr.wooco.woocobe.auth.domain.model.SocialType
import kr.wooco.woocobe.auth.infrastructure.storage.AuthTokenEntity
import kr.wooco.woocobe.auth.infrastructure.storage.AuthTokenRedisRepository
import kr.wooco.woocobe.auth.infrastructure.storage.entity.AuthUserJpaEntity
import kr.wooco.woocobe.auth.infrastructure.storage.repository.AuthUserJpaRepository
import kr.wooco.woocobe.support.IntegrationTest
import kr.wooco.woocobe.support.MysqlCleaner
import kr.wooco.woocobe.support.RedisCleaner
import kr.wooco.woocobe.user.infrastructure.storage.UserEntity
import kr.wooco.woocobe.user.infrastructure.storage.UserJpaRepository
import org.springframework.data.repository.findByIdOrNull

@IntegrationTest
class WithdrawUseCaseTest(
    private val withdrawUseCase: WithdrawUseCase,
    private val userJpaRepository: UserJpaRepository,
    private val authUserJpaRepository: AuthUserJpaRepository,
    private val authTokenRedisRepository: AuthTokenRedisRepository,
) : BehaviorSpec({
    listeners(MysqlCleaner(), RedisCleaner())

    Given("유효한 input 값이 들어올 경우") {
        val validUserId = 9876543210L
        val validAuthTokenId = 1234567890L
        val validRefreshToken = "eyJhbGciOiJIUzM4NCJ9" +
                ".eyJ0b2tlbl9pZCI6MTIzNDU2Nzg5MCwiZXhwIjo5OTk5OTk5OTk5fQ" +
                ".ksUhjX5K1edXRQwlDwA40i_oACT9CubMdeiqOX3XBzhyfbNh5DXISg2oiXKpJg6N"

        val input = WithdrawInput(userId = validUserId, refreshToken = validRefreshToken)

        When("해당 회원 정보가 존재할 때") {
            val userEntity = UserEntity(id = validUserId, name = "", profileUrl = "").run(userJpaRepository::save)
            val authUserJpaEntity = AuthUserJpaEntity(
                id = 1234567890L,
                userId = validUserId,
                socialId = "1234567890",
                socialType = SocialType.KAKAO.name,
            ).run(authUserJpaRepository::save)
            val authToken = AuthTokenEntity(id = validAuthTokenId, userId = validUserId)
                .run(authTokenRedisRepository::save)

            withdrawUseCase.execute(input)

            Then("회원 정보가 삭제된다.") {
                val expectNull = userJpaRepository.findByIdOrNull(userEntity.id)
                expectNull.shouldBeNull()
            }

            Then("인증 회원 정보가 삭제된다.") {
                val expectNull = authUserJpaRepository.findByIdOrNull(authUserJpaEntity.id)
                expectNull.shouldBeNull()
            }

            Then("인증 토큰이 삭제된다.") {
                val expectNull = authTokenRedisRepository.findById(authToken.id)
                expectNull.shouldBeNull()
            }
        }

        When("회원 정보가 존재하지 안") {
        }
    }
})
