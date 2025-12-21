package kr.wooco.woocobe.core.schedule.application.port.out

import kr.wooco.woocobe.core.schedule.domain.entity.Plan

interface SchedulePlanCommandPort {

    fun save(plan: Plan): Long

    fun getById(planId: Long): Plan
}
