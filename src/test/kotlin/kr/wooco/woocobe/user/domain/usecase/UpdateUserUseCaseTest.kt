package kr.wooco.woocobe.user.domain.usecase

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kr.wooco.woocobe.support.IntegrationTest
import kr.wooco.woocobe.support.MysqlCleaner
import kr.wooco.woocobe.user.infrastructure.storage.entity.UserEntity
import kr.wooco.woocobe.user.infrastructure.storage.repository.UserJpaRepository

@IntegrationTest
class UpdateUserUseCaseTest(
    private val updateUserUseCase: UpdateUserUseCase,
    private val userJpaRepository: UserJpaRepository,
) : BehaviorSpec({
    listeners(MysqlCleaner())

    Given("유효한 input 값이 들어올 경우") {
        val validUserId = 1234567890L
        val updatedName = "홍이름바꿈"
        val updatedProfileUrl = "url-hong"

        val input = UpdateUserInput(userId = validUserId, name = updatedName, profileUrl = updatedProfileUrl)

        When("존재하는 회원일 때") {
            UserEntity(id = validUserId, name = "홍인데유", profileUrl = "url").run(userJpaRepository::save)

            updateUserUseCase.execute(input)

            Then("회원 정보가 변경된다.") {
                val userEntities = userJpaRepository.findAll()
                userEntities.size shouldBe 1

                userEntities[0].id shouldBe validUserId
                userEntities[0].name shouldBe updatedName
                userEntities[0].profileUrl shouldBe updatedProfileUrl
            }
        }
    }
})
