package kr.wooco.woocobe.auth.domain.usecase

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kr.wooco.woocobe.auth.domain.gateway.AuthUserStorageGateway
import kr.wooco.woocobe.auth.domain.gateway.SocialAuthClientGateway
import kr.wooco.woocobe.auth.domain.gateway.TokenProviderGateway
import kr.wooco.woocobe.auth.domain.model.AuthUser
import kr.wooco.woocobe.auth.domain.model.SocialAuthInfo
import kr.wooco.woocobe.auth.domain.model.SocialAuthType
import kr.wooco.woocobe.auth.infrastructure.storage.AuthUserJpaRepository
import kr.wooco.woocobe.support.IntegrationTest
import kr.wooco.woocobe.support.MysqlCleaner
import kr.wooco.woocobe.support.RedisCleaner
import kr.wooco.woocobe.user.domain.gateway.UserStorageGateway
import kr.wooco.woocobe.user.domain.model.User
import kr.wooco.woocobe.user.infrastructure.storage.UserJpaRepository

@IntegrationTest
class SocialLoginUseCaseTest(
    private val userStorageGateway: UserStorageGateway,
    private val tokenProviderGateway: TokenProviderGateway,
    private val authUserStorageGateway: AuthUserStorageGateway,
    private val userJpaRepository: UserJpaRepository,
    private val authUserJpaRepository: AuthUserJpaRepository,
) : BehaviorSpec({
    listeners(MysqlCleaner(), RedisCleaner())

    val socialAuthClientGateway = mockk<SocialAuthClientGateway>()
    val socialAuthInfo = SocialAuthInfo.register(socialId = "1234567890", socialType = "kakao")
    every { socialAuthClientGateway.getSocialAuthInfo(socialToken = "kakao_social_token") } returns socialAuthInfo

    val socialLoginUseCase = SocialLoginUseCase(
        userStorageGateway,
        tokenProviderGateway,
        authUserStorageGateway,
        socialAuthClientGateway,
    )

    Given("유효한 input 값이 들어올 때") {
        val input = SocialLoginInput(socialType = "kakao", socialToken = "kakao_social_token")

        When("존재하지 않는 인증 정보일 경우") {
            val sut = socialLoginUseCase.execute(input)

            Then("access 토큰과 refresh 토큰을 발급한다.") {
                println(sut.refreshToken)
                sut.accessToken.shouldNotBeNull()
                sut.refreshToken.shouldNotBeNull()
            }

            Then("새로운 인증 정보를 저장한다.") {
                val authUsers = authUserJpaRepository.findAll()
                authUsers.size shouldBe 1
            }

            Then("새로운 회원 정보를 저장한다.") {
                val users = userJpaRepository.findAll()
                users.size shouldBe 1
            }
        }

        When("존재하는 인증 정보일 경우") {
            val authUser = AuthUser.register(socialId = "1234567890", socialType = SocialAuthType.KAKAO).let {
                authUserStorageGateway.save(it)
            }
            val user = User.register(authUser.userId).let {
                userStorageGateway.save(it)
            }

            socialLoginUseCase.execute(input)

            Then("새로운 인증 정보를 저장하지 않는다.") {
                val authUserEntities = authUserJpaRepository.findAll()
                authUserEntities.size shouldBe 1
                authUserEntities[0].id shouldBe authUser.id
            }

            Then("새로운 회원 정보를 저장하지 않는다.") {
                val userEntities = userJpaRepository.findAll()
                userEntities.size shouldBe 1
                userEntities[0].id shouldBe user.id
            }
        }
    }
})
