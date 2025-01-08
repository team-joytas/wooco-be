package kr.wooco.woocobe.common.infrastructure.storage

import com.github.f4b6a3.tsid.TsidFactory

@Deprecated(message = "이 클래스는 더 이상 사용하지 않습니다.")
object IdGenerator {
    private val tsidFactory: TsidFactory = TsidFactory
        .builder()
        .withNode(IdGeneratorConfig.node)
        .build()

    fun generateId(): Long = tsidFactory.create().toLong()
}
