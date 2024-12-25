package kr.wooco.woocobe.common.infrastructure.storage

import com.github.f4b6a3.tsid.TsidFactory

object IdGenerator {
    private val tsidFactory: TsidFactory = TsidFactory
        .builder()
        .withNode(IdGeneratorConfig.node)
        .build()

    fun generateId(): Long = tsidFactory.create().toLong()
}
