package kr.wooco.woocobe.common.domain

import com.github.f4b6a3.tsid.TsidFactory
import kr.wooco.woocobe.common.config.IdGeneratorConfig

object IdGenerator {
    private val tsidFactory: TsidFactory = TsidFactory.builder()
        .withNode(IdGeneratorConfig.node)
        .build()

    fun generateId(): Long = tsidFactory.create().toLong()
}
