package kr.wooco.woocobe.plan.domain.model

import kr.wooco.woocobe.plan.domain.exception.UnSupportCategoryException

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
                ?: throw UnSupportCategoryException
    }
}
