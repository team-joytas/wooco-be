package kr.wooco.woocobe.plan.domain.exception

import kr.wooco.woocobe.common.domain.exception.CustomException
import org.springframework.http.HttpStatus

sealed class BasePlanException(
    code: String,
    message: String,
    status: HttpStatus,
) : CustomException(code = code, message = message, status = status)

data object InvalidPlanWriterException : BasePlanException(
    code = "INVALID_PLAN_WRITER",
    message = "해당 플랜의 작성자가 아닙니다.",
    status = HttpStatus.FORBIDDEN,
) {
    private fun readResolve(): Any = InvalidPlanWriterException
}

data object NotExistsPlanException : BasePlanException(
    code = "NOT_EXISTS_PLAN",
    message = "존재하지 않는 플랜입니다.",
    status = HttpStatus.NOT_FOUND,
) {
    private fun readResolve(): Any = NotExistsPlanException
}

data object UnSupportCategoryException : BasePlanException(
    code = "UN_SUPPORT_CATEGORY",
    message = "지원하지 않는 카테고리 형식입니다.",
    status = HttpStatus.BAD_REQUEST,
) {
    private fun readResolve(): Any = UnSupportCategoryException
}
