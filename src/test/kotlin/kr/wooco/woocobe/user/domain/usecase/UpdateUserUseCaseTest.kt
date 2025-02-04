// package kr.wooco.woocobe.user.domain.usecase
//
// import io.kotest.core.spec.style.BehaviorSpec
// import io.kotest.matchers.shouldBe
// import kr.wooco.woocobe.support.IntegrationTest
// import kr.wooco.woocobe.support.MysqlCleaner
// import kr.wooco.woocobe.user.adapter.out.persistence.entity.UserJpaEntity
// import kr.wooco.woocobe.user.adapter.out.persistence.repository.UserJpaRepository
//
// @IntegrationTest
// class UpdateUserUseCaseTest(
//    private val updateUserUseCase: UpdateUserUseCase,
//    private val userJpaRepository: UserJpaRepository,
// ) : BehaviorSpec({
//    listeners(MysqlCleaner())
//
//    Given("저장된 회원이 존재하는 경우") {
//        val userEntity =
//            UserJpaEntity(name = "홍인데유", profileUrl = "url", description = "한줄소개").run(userJpaRepository::save)
//        val validUserId = userEntity.id
//
//        val updatedName = "홍이름바꿈"
//        val updatedProfileUrl = "url-hong"
//        val updatedDescription = "변경된 소개 문장"
//
//        When("해당 회원의 정보를 수정할 때") {
//            val input = UpdateUserInput(
//                userId = validUserId,
//                name = updatedName,
//                profileUrl = updatedProfileUrl,
//                description = updatedDescription,
//            )
//
//            updateUserUseCase.execute(input)
//
//            Then("회원 정보가 변경된다.") {
//                val userEntities = userJpaRepository.findAll()
//                userEntities.size shouldBe 1
//
//                userEntities[0].id shouldBe validUserId
//                userEntities[0].name shouldBe updatedName
//                userEntities[0].profileUrl shouldBe updatedProfileUrl
//                userEntities[0].description shouldBe updatedDescription
//            }
//        }
//    }
// })
