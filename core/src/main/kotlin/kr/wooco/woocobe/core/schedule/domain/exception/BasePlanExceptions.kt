package kr.wooco.woocobe.core.schedule.domain.exception

import kr.wooco.woocobe.common.exception.CustomException

sealed class BasePlanException(
    code: String,
    message: String,
) : CustomException(code = code, message = message)

data object PlanAlreadyDeletedException : BasePlanException(
    code = "ALREADY_DELETED_PLAN",
    message = "이미 삭제된 플랜입니다.",
) {
    private fun readResolve(): Any = PlanAlreadyDeletedException
}

data object NotExistsPlanException : BasePlanException(
    code = "NOT_EXISTS_PLAN",
    message = "존재하지 않는 플랜입니다.",
) {
    private fun readResolve(): Any = NotExistsPlanException
}

data object PlanAccessDeniedException : BasePlanException(
    code = "PLAN_ACCESS_DENIED",
    message = "플랜 접근 권한이 없습니다.",
) {
    private fun readResolve(): Any = PlanAccessDeniedException
}
