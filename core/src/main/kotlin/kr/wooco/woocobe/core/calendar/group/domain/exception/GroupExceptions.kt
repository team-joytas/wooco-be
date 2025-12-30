package kr.wooco.woocobe.core.calendar.group.domain.exception

import kr.wooco.woocobe.common.exception.CustomException

sealed class BaseGroupException(
    code: String,
    message: String,
) : CustomException(code = code, message = message)

data object NotExistsGroupException : BaseGroupException(
    code = "NOT_EXISTS_GROUP",
    message = "존재하지 않는 그룹입니다.",
) {
    private fun readResolve(): Any = NotExistsGroupException
}

data object GroupAlreadyDeletedException : BaseGroupException(
    code = "ALREADY_DELETED_GROUP",
    message = "이미 삭제된 그룹입니다.",
) {
    private fun readResolve(): Any = GroupAlreadyDeletedException
}

data object InvalidGroupOperationException : BaseGroupException(
    code = "INVALID_GROUP_OPERATION",
    message = "올바르지 않은 그룹 요청입니다.",
) {
    private fun readResolve(): Any = InvalidGroupOperationException
}

data object GroupUserLimitExceededException : BaseGroupException(
    code = "GROUP_USER_LIMIT_EXCEEDED",
    message = "해당 그룹의 정원을 초과할 수 없습니다.",
) {
    private fun readResolve(): Any = GroupUserLimitExceededException
}

data object AlreadyGroupMemberException : BaseGroupException(
    code = "ALREADY_GROUP_USER_EXIST",
    message = "이미 해당 그룹에 참여한 유저입니다.",
) {
    private fun readResolve(): Any = AlreadyGroupMemberException
}

data object NotGroupMemberException : BaseGroupException(
    code = "NOT_GROUP_USER",
    message = "해당 그룹에 소속된 유저가 아닙니다.",
) {
    private fun readResolve(): Any = NotGroupMemberException
}

data object NotGroupOwnerException : BaseGroupException(
    code = "INVALID_GROUP_OWNER",
    message = "해당 그룹의 관리자가 아닙니다.",
) {
    private fun readResolve(): Any = NotGroupOwnerException
}

data object GroupHasUserException : BaseGroupException(
    code = "GROUP_USER_EXISTS",
    message = "그룹에 소속된 유저가 존재합니다.",
) {
    private fun readResolve(): Any = GroupHasUserException
}

data object InvalidInviteCodeException : BaseGroupException(
    code = "INVALID_INVITE_CODE",
    message = "유효하지 않은 그룹 초대 코드입니다.",
) {
    private fun readResolve(): Any = InvalidInviteCodeException
}
