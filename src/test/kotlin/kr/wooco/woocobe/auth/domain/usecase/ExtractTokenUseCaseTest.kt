package kr.wooco.woocobe.auth.domain.usecase

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kr.wooco.woocobe.support.IntegrationTest

@IntegrationTest
class ExtractTokenUseCaseTest(
    private val extractTokenUseCase: ExtractTokenUseCase,
) : BehaviorSpec({
    Given("유효한 input 값이 들어올 경우") {
        val validUserId = 9876543210L
        val validAccessToken = "eyJhbGciOiJIUzM4NCJ9" +
                ".eyJ1c2VyX2lkIjo5ODc2NTQzMjEwLCJleHAiOjk5OTk5OTk5OTl9" +
                ".tOxoAKKOPqWDbXdEykgAiVVW4xu0G0hCRZVSlhzY5R_s2mDQ_kOwz_npESOo-Ug3"

        val input = ExtractTokenInput(accessToken = validAccessToken)

        When("토큰을 추출했을 때") {
            val sut = extractTokenUseCase.execute(input)

            Then("정상적으로 회원 식별자 값이 반환된다.") {
                sut.userId shouldBe validUserId
            }
        }
    }
})
