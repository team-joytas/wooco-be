package kr.wooco.woocobe.mysql.common.utils

import kr.wooco.woocobe.common.tsid.TsidGenerator
import org.hibernate.annotations.IdGeneratorType
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.IdentifierGenerator

internal class TsidIdentifierGenerator : IdentifierGenerator {
    override fun generate(
        session: SharedSessionContractImplementor?,
        `object`: Any?,
    ): Any = TsidGenerator.generateToLong()
}

@IdGeneratorType(TsidIdentifierGenerator::class)
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Tsid
