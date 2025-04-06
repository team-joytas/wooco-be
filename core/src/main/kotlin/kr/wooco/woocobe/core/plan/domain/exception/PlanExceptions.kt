package kr.wooco.woocobe.core.plan.domain.exception

import kr.wooco.woocobe.common.exception.CustomException

sealed class BasePlanException(
    code: String,
    message: String,
) : CustomException(code = code, message = message)

data object InvalidPlanWriterException : BasePlanException(
    code = "INVALID_PLAN_WRITER",
    message = "해당 플랜의 작성자가 아닙니다.",
) {
    private fun readResolve(): Any = InvalidPlanWriterException
}

data object NotExistsPlanException : BasePlanException(
    code = "NOT_EXISTS_PLAN",
    message = "존재하지 않는 플랜입니다.",
) {
    private fun readResolve(): Any = NotExistsPlanException
}

data object AlreadyDeletedPlanException : BasePlanException(
    code = "ALREADY_DELETED_PLAN_EXCEPTION",
    message = "이미 삭제된 플랜입니다.",
) {
    private fun readResolve(): Any = AlreadyDeletedPlanException
}
