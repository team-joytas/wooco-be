package kr.wooco.woocobe.user.domain.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kr.wooco.woocobe.support.IntegrationTest
import kr.wooco.woocobe.support.MysqlCleaner
import kr.wooco.woocobe.user.infrastructure.storage.entity.UserJpaEntity
import kr.wooco.woocobe.user.infrastructure.storage.repository.UserJpaRepository

@IntegrationTest
class GetUserUseCaseTest(
    private val getUserUseCase: GetUserUseCase,
    private val userJpaRepository: UserJpaRepository,
) : BehaviorSpec({
    listeners(MysqlCleaner())

    Given("저장된 회원이 존재할 경우") {
        val userJpaEntity = UserJpaEntity(
            name = "홍인데요",
            profileUrl = "url",
            description = "소개 문장",
        ).run(userJpaRepository::save)

        val validUserId = userJpaEntity.id

        When("해당 회원 식별자 값이 주어질 때") {
            val input = GetUserInput(userId = validUserId)

            val sut = getUserUseCase.execute(input)

            Then("사용자 정보를 반환한다.") {
                val user = sut.user
                user.id shouldBe userJpaEntity.id
                user.name shouldBe userJpaEntity.name
                user.profileUrl shouldBe userJpaEntity.profileUrl
            }
        }

        When("일치하지 않는 식별자 값이 주어질 때") {
            val input = GetUserInput(userId = 0L)

            Then("NotExistsUserException 오류가 발생한다.") {
                shouldThrow<RuntimeException> {
                    getUserUseCase.execute(input)
                }
            }
        }
    }
})
