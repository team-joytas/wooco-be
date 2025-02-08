package kr.wooco.woocobe.plan.application.port.out

interface DeletePlanPersistencePort {
    fun deleteByPlanId(planId: Long)
}
