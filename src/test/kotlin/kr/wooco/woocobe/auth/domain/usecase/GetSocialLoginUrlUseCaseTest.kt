// package kr.wooco.woocobe.auth.domain.usecase
//
// import io.kotest.core.spec.style.BehaviorSpec
// import io.kotest.matchers.nulls.shouldNotBeNull
// import io.kotest.matchers.shouldBe
// import kr.wooco.woocobe.support.IntegrationTest
// import kr.wooco.woocobe.support.RedisCleaner
// import org.springframework.data.redis.core.StringRedisTemplate
//
// @IntegrationTest
// class GetSocialLoginUrlUseCaseTest(
//    private val stringRedisTemplate: StringRedisTemplate,
//    private val getSocialLoginUrlUseCase: GetSocialLoginUrlUseCase,
// ) : BehaviorSpec({
//    listeners(RedisCleaner())
//
//    Given("유효한 input 값이 들어올 경우") {
//        val validSocialType = "kakao"
//
//        val input = GetSocialLoginUrlInput(socialType = validSocialType)
//
//        When("존재하는 소셜 로그인 제공자의 URL을 요청할 때") {
//            val sut = getSocialLoginUrlUseCase.execute(input)
//
//            Then("정상적으로 로그인 URL과 code challenge 값을 반환한다.") {
//                sut.loginUrl.shouldNotBeNull()
//                sut.challenge.shouldNotBeNull()
//            }
//
//            Then("PKCE를 저장한다.") {
//                val pkceEntities = stringRedisTemplate.keys("pkce:*")
//                pkceEntities.size shouldBe 1
//            }
//        }
//    }
// })
