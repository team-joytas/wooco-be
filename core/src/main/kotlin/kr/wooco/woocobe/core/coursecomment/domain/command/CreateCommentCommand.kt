package kr.wooco.woocobe.core.coursecomment.domain.command

import kr.wooco.woocobe.core.coursecomment.domain.entity.CourseComment

data class CreateCommentCommand(
    val userId: Long,
    val courseId: Long,
    val contents: CourseComment.Contents,
    // FIXME: 아래 두 필드 이벤트 발행 때문에 임시로 추가 -> 준투랑 이야기 후 제거
    val courseTitle: String,
    val courseWriterId: Long,
)
