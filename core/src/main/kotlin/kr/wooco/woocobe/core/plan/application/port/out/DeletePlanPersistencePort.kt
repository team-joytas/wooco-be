package kr.wooco.woocobe.core.plan.application.port.out

interface DeletePlanPersistencePort {
    fun deleteByPlanId(planId: Long)
}
