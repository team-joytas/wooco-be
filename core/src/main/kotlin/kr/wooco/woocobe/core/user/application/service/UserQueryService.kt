package kr.wooco.woocobe.core.user.application.service

import kr.wooco.woocobe.core.course.application.port.out.LoadCoursePersistencePort
import kr.wooco.woocobe.core.course.application.port.out.LoadInterestCoursePersistencePort
import kr.wooco.woocobe.core.placereview.application.port.out.PlaceReviewQueryPort
import kr.wooco.woocobe.core.user.application.port.`in`.ReadUserDetailsUseCase
import kr.wooco.woocobe.core.user.application.port.`in`.ReadUserInfoUseCase
import kr.wooco.woocobe.core.user.application.port.`in`.results.UserDetailsResult
import kr.wooco.woocobe.core.user.application.port.`in`.results.UserInfoResult
import kr.wooco.woocobe.core.user.application.port.out.UserQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
internal class UserQueryService(
    private val userQueryPort: UserQueryPort,
    private val placeReviewQueryPort: PlaceReviewQueryPort,
    private val loadCoursePersistencePort: LoadCoursePersistencePort,
    private val loadInterestCoursePersistencePort: LoadInterestCoursePersistencePort,
) : ReadUserInfoUseCase,
    ReadUserDetailsUseCase {
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun readUserInfo(query: ReadUserInfoUseCase.Query): UserInfoResult {
        val user = userQueryPort.getByUserId(query.userId)
        return UserInfoResult.fromUser(user)
    }

    // TODO: 이벤트 리스너로 read model 업데이트 방식으로 수정
    @Transactional(readOnly = true)
    override fun readUserDetails(query: ReadUserDetailsUseCase.Query): UserDetailsResult {
        val user = userQueryPort.getByUserId(query.userId)
        val reviewCount = placeReviewQueryPort.countByUserId(query.userId)
        val courseCount = loadCoursePersistencePort.countByUserId(query.userId)
        val interestCourseCount = loadInterestCoursePersistencePort.countByUserId(query.userId)
        return UserDetailsResult.of(user, reviewCount, courseCount, interestCourseCount)
    }
}
