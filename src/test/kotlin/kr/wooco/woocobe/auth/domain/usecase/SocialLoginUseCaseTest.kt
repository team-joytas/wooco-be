package kr.wooco.woocobe.auth.domain.usecase

import io.kotest.core.spec.style.BehaviorSpec
import kr.wooco.woocobe.auth.domain.gateway.AuthTokenStorageGateway
import kr.wooco.woocobe.auth.domain.gateway.AuthUserStorageGateway
import kr.wooco.woocobe.auth.domain.gateway.PkceStorageGateway
import kr.wooco.woocobe.auth.domain.gateway.TokenProviderGateway
import kr.wooco.woocobe.auth.infrastructure.cache.repository.PkceRedisRepository
import kr.wooco.woocobe.auth.infrastructure.storage.repository.AuthUserJpaRepository
import kr.wooco.woocobe.support.IntegrationTest
import kr.wooco.woocobe.support.MysqlCleaner
import kr.wooco.woocobe.support.RedisCleaner
import kr.wooco.woocobe.user.domain.gateway.UserStorageGateway
import kr.wooco.woocobe.user.infrastructure.storage.UserJpaRepository
import org.springframework.data.redis.core.StringRedisTemplate

@IntegrationTest
class SocialLoginUseCaseTest(
    private val userStorageGateway: UserStorageGateway,
    private val pkceStorageGateway: PkceStorageGateway,
    private val tokenProviderGateway: TokenProviderGateway,
    private val authTokenStorageGateway: AuthTokenStorageGateway,
    private val authUserStorageGateway: AuthUserStorageGateway,
    private val stringRedisTemplate: StringRedisTemplate,
    private val userJpaRepository: UserJpaRepository,
    private val authUserJpaRepository: AuthUserJpaRepository,
    private val pkceRedisRepository: PkceRedisRepository,
) : BehaviorSpec({
    listeners(MysqlCleaner(), RedisCleaner())

//    val mockAuthCode = "kakao_social_token"
//
//    val socialAuthClientGateway = mockk<SocialAuthClientGateway>()
//    val socialAuth = SocialAuth(socialId = "1234567890", socialType = SocialType.KAKAO)
//
//    every {
//        socialAuthClientGateway.fetchSocialAuth(
//            authCode = mockAuthCode,
//            socialType = SocialType.KAKAO,
//            pkce = any(),
//        )
//    } returns socialAuth
//
//    val socialLoginUseCase = SocialLoginUseCase(
//        userStorageGateway = userStorageGateway,
//        pkceStorageGateway = pkceStorageGateway,
//        tokenProviderGateway = tokenProviderGateway,
//        authUserStorageGateway = authUserStorageGateway,
//        authTokenStorageGateway = authTokenStorageGateway,
//        socialAuthClientGateway = socialAuthClientGateway,
//    )
//
//    Given("유효한 input 값이 들어올 경우") {
//        val validPkce = Pkce.register()
//
//        val input = SocialLoginInput(socialType = "kakao", authCode = mockAuthCode, challenge = validPkce.challenge)
//
//        When("존재하지 않는 인증 정보일 때") {
//            PkceRedisEntity(
//                verifier = validPkce.verifier,
//                challenge = validPkce.challenge,
//            ).run(pkceRedisRepository::save)
//
//            val sut = socialLoginUseCase.execute(input)
//
//            Then("access 토큰과 refresh 토큰을 발급한다.") {
//                sut.accessToken.shouldNotBeNull()
//                sut.refreshToken.shouldNotBeNull()
//            }
//
//            Then("새로운 인증 토큰을 저장한다.") {
//                val authTokenEntities = stringRedisTemplate.keys("token:*")
//                authTokenEntities.size shouldBe 1
//            }
//
//            Then("새로운 인증 정보를 저장한다.") {
//                val authUsers = authUserJpaRepository.findAll()
//                authUsers.size shouldBe 1
//
//                authUsers[0].userId.shouldNotBeNull()
//            }
//
//            Then("새로운 회원 정보를 저장한다.") {
//                val users = userJpaRepository.findAll()
//                users.size shouldBe 1
//            }
//
//            Then("저장된 PKCE를 삭제한다") {
//                val pkceEntities = stringRedisTemplate.keys("pkce:*")
//                pkceEntities.size shouldBe 0
//            }
//        }
//
//        When("존재하는 인증 정보일 때") {
//            PkceRedisEntity(
//                verifier = validPkce.verifier,
//                challenge = validPkce.challenge,
//            ).run(pkceRedisRepository::save)
//
//            val userEntity = UserEntity(id = 1234567890L, name = "", profileUrl = "").run(userJpaRepository::save)
//            val authUserJpaEntity = AuthUserJpaEntity(
//                id = 1234567890L,
//                userId = userEntity.id,
//                socialId = socialAuth.socialId,
//                socialType = socialAuth.socialType.name,
//            ).run(authUserJpaRepository::save)
//
//            socialLoginUseCase.execute(input)
//
//            Then("새로운 인증 정보를 저장하지 않는다.") {
//                val authUserEntities = authUserJpaRepository.findAll()
//                authUserEntities.size shouldBe 1
//                authUserEntities[0].id shouldBe authUserJpaEntity.id
//            }
//
//            Then("새로운 회원 정보를 저장하지 않는다.") {
//                val userEntities = userJpaRepository.findAll()
//                userEntities.size shouldBe 1
//                userEntities[0].id shouldBe userEntity.id
//            }
//
//            Then("저장된 PKCE를 삭제한다") {
//                val pkceEntities = stringRedisTemplate.keys("pkce:*")
//                pkceEntities.size shouldBe 0
//            }
//        }
//    }
})
