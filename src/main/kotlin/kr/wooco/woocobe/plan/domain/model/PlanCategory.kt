package kr.wooco.woocobe.plan.domain.model

enum class PlanCategory {
    FAMOUS_RESTAURANT,
    ACTIVITY,
    HOT_PLACE,
    CULTURE_AND_ART,
    ETC,
    ;

    companion object {
        fun from(viewName: String) =
            PlanCategory.entries.find { it.name == viewName }
                ?: throw RuntimeException("not found plan category $viewName")
    }
}
