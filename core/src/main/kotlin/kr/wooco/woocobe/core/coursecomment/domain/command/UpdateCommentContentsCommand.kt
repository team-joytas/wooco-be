package kr.wooco.woocobe.core.coursecomment.domain.command

import kr.wooco.woocobe.core.coursecomment.domain.entity.CourseComment

data class UpdateCommentContentsCommand(
    val userId: Long,
    val contents: CourseComment.Contents,
)
