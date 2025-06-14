package kr.wooco.woocobe.core.course.domain.vo

data class CourseContent(
    val title: String,
    val contents: String,
) {
    init {
        require(title.length in 2..20) { "제목은 2자 이상 20자 이하만 가능합니다." }
        require(contents.length in 2..200) { "내용은 2자 이상 200자 이하만 가능합니다." }
    }
}
