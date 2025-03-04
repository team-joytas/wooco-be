package kr.wooco.woocobe.mysql.common.utils

import com.github.f4b6a3.tsid.TsidCreator
import org.hibernate.annotations.IdGeneratorType
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.IdentifierGenerator

internal class TsidGenerator : IdentifierGenerator {
    override fun generate(
        session: SharedSessionContractImplementor?,
        `object`: Any?,
    ): Any = TsidCreator.getTsid().toLong()
}

@IdGeneratorType(TsidGenerator::class)
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Tsid
