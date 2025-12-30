package kr.wooco.woocobe.core.calendar.schedule.application.port.out

import kr.wooco.woocobe.core.calendar.schedule.domain.entity.Plan

interface SchedulePlanCommandPort {

    fun save(plan: Plan): Long

    fun getById(planId: Long): Plan
}
