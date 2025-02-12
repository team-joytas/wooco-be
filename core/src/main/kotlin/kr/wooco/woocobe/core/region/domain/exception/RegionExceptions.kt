package kr.wooco.woocobe.core.region.domain.exception

import kr.wooco.woocobe.core.common.exception.CustomException
import org.springframework.http.HttpStatus

sealed class RegionException(
    code: String,
    message: String,
    status: HttpStatus,
) : CustomException(code = code, message = message, status = status)

data object AlreadyPreferenceRegionException : RegionException(
    code = "ALREADY_PREFERENCE_REGION",
    message = "이미 관심 지역으로 설정된 지역입니다.",
    status = HttpStatus.CONFLICT,
) {
    private fun readResolve(): Any = AlreadyPreferenceRegionException
}

data object NotExistsPreferenceRegionException : RegionException(
    code = "NOT_EXISTS_PREFERENCE_REGION",
    message = "관심 지역으로 설정되지 않은 지역입니다.",
    status = HttpStatus.NOT_FOUND,
) {
    private fun readResolve(): Any = NotExistsPreferenceRegionException
}
