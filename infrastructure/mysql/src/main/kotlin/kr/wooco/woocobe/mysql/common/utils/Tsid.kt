package kr.wooco.woocobe.mysql.common.utils

import kr.wooco.woocobe.common.tsid.TsidGenerator
import kr.wooco.woocobe.mysql.common.entity.BaseEntity
import org.hibernate.annotations.IdGeneratorType
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.IdentifierGenerator

internal class TsidIdentifierGenerator : IdentifierGenerator {
    override fun generate(
        session: SharedSessionContractImplementor?,
        `object`: Any?,
    ): Any {
        if (`object` is BaseEntity && `object`.id != 0L) {
            return `object`.id
        }
        return TsidGenerator.generateToLong()
    }
}

@Deprecated(message = "엔티티의 식별자 생성 전략은 domain 엔티티 측으로 옮겨주세요.")
@IdGeneratorType(TsidIdentifierGenerator::class)
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Tsid
