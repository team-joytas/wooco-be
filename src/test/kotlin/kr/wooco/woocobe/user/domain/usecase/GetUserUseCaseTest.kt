package kr.wooco.woocobe.user.domain.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kr.wooco.woocobe.support.IntegrationTest
import kr.wooco.woocobe.support.MysqlCleaner
import kr.wooco.woocobe.user.infrastructure.storage.UserEntity
import kr.wooco.woocobe.user.infrastructure.storage.UserJpaRepository

@IntegrationTest
class GetUserUseCaseTest(
    private val getUserUseCase: GetUserUseCase,
    private val userJpaRepository: UserJpaRepository,
) : BehaviorSpec({
    listeners(MysqlCleaner())

    Given("유효한 input 값이 들어올 경우") {
        val validUserId = 1234567890L

        val input = GetUserInput(userId = validUserId)

        When("해당 사용자가 존재할 때") {
            val userEntity = UserEntity(
                id = validUserId,
                name = "홍인데요",
                profileUrl = "url",
            ).run(userJpaRepository::save)

            val sut = getUserUseCase.execute(input)

            Then("사용자 정보를 반환한다.") {
                val user = sut.user
                user.id shouldBe userEntity.id
                user.name shouldBe userEntity.name
                user.profileUrl shouldBe userEntity.profileUrl
            }
        }

        When("해당 사용자가 존재하지 않을 때") {
            Then("NotExistsUserException 오류가 발생한다.") {
                shouldThrow<RuntimeException> {
                    getUserUseCase.execute(input)
                }
            }
        }
    }
})
