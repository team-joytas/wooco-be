package kr.wooco.woocobe.auth.domain.usecase

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kr.wooco.woocobe.auth.domain.gateway.AuthTokenStorageGateway
import kr.wooco.woocobe.auth.domain.gateway.AuthUserStorageGateway
import kr.wooco.woocobe.auth.domain.gateway.SocialAuthClientGateway
import kr.wooco.woocobe.auth.domain.gateway.TokenProviderGateway
import kr.wooco.woocobe.auth.domain.model.SocialAuth
import kr.wooco.woocobe.auth.domain.model.SocialType
import kr.wooco.woocobe.auth.infrastructure.storage.AuthUserEntity
import kr.wooco.woocobe.auth.infrastructure.storage.AuthUserJpaRepository
import kr.wooco.woocobe.support.IntegrationTest
import kr.wooco.woocobe.support.MysqlCleaner
import kr.wooco.woocobe.support.RedisCleaner
import kr.wooco.woocobe.user.domain.gateway.UserStorageGateway
import kr.wooco.woocobe.user.infrastructure.storage.UserEntity
import kr.wooco.woocobe.user.infrastructure.storage.UserJpaRepository
import org.springframework.data.redis.core.StringRedisTemplate

@IntegrationTest
class SocialLoginUseCaseTest(
    private val userStorageGateway: UserStorageGateway,
    private val tokenProviderGateway: TokenProviderGateway,
    private val authTokenStorageGateway: AuthTokenStorageGateway,
    private val authUserStorageGateway: AuthUserStorageGateway,
    private val stringRedisTemplate: StringRedisTemplate,
    private val userJpaRepository: UserJpaRepository,
    private val authUserJpaRepository: AuthUserJpaRepository,
) : BehaviorSpec({
    listeners(MysqlCleaner(), RedisCleaner())

    val mockAuthCode = "kakao_social_token"

    val socialAuthClientGateway = mockk<SocialAuthClientGateway>()
    val socialAuth = SocialAuth(socialId = "1234567890", socialType = SocialType.KAKAO)

    every {
        socialAuthClientGateway.fetchSocialAuth(
            authCode = mockAuthCode,
            socialType = SocialType.KAKAO,
        )
    } returns socialAuth

    val socialLoginUseCase = SocialLoginUseCase(
        userStorageGateway = userStorageGateway,
        tokenProviderGateway = tokenProviderGateway,
        authUserStorageGateway = authUserStorageGateway,
        authTokenStorageGateway = authTokenStorageGateway,
        socialAuthClientGateway = socialAuthClientGateway,
    )

    Given("유효한 input 값이 들어올 경우") {
        val input = SocialLoginInput(socialType = "kakao", authCode = mockAuthCode)

        When("존재하지 않는 인증 정보일 때") {
            val sut = socialLoginUseCase.execute(input)

            Then("access 토큰과 refresh 토큰을 발급한다.") {
                sut.accessToken.shouldNotBeNull()
                sut.refreshToken.shouldNotBeNull()
            }

            Then("새로운 인증 토큰을 저장한다.") {
                val authTokens = stringRedisTemplate.keys("*")
                authTokens.size shouldBe 1
            }

            Then("새로운 인증 정보를 저장한다.") {
                val authUsers = authUserJpaRepository.findAll()
                authUsers.size shouldBe 1

                authUsers[0].userId.shouldNotBeNull()
            }

            Then("새로운 회원 정보를 저장한다.") {
                val users = userJpaRepository.findAll()
                users.size shouldBe 1
            }
        }

        When("존재하는 인증 정보일 때") {
            val userEntity = UserEntity(id = 1234567890L, name = "", profileUrl = "").run(userJpaRepository::save)
            val authUserEntity = AuthUserEntity(
                id = 1234567890L,
                userId = userEntity.id,
                socialId = socialAuth.socialId,
                socialType = socialAuth.socialType,
            ).run(authUserJpaRepository::save)

            socialLoginUseCase.execute(input)

            Then("새로운 인증 정보를 저장하지 않는다.") {
                val authUserEntities = authUserJpaRepository.findAll()
                authUserEntities.size shouldBe 1
                authUserEntities[0].id shouldBe authUserEntity.id
            }

            Then("새로운 회원 정보를 저장하지 않는다.") {
                val userEntities = userJpaRepository.findAll()
                userEntities.size shouldBe 1
                userEntities[0].id shouldBe userEntity.id
            }
        }
    }
})
