package kr.wooco.woocobe.core.common.domain.entity

import kr.wooco.woocobe.common.tsid.TsidGenerator

abstract class DomainEntity {
    abstract val id: Long

    override fun hashCode(): Int = id.hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DomainEntity

        return id == other.id
    }

    override fun toString(): String = "${this::class.simpleName}(id=$id)"

    companion object {
        /**
         * Long 타입의 TSID를 생성합니다.
         *
         * @return Long
         * @author JiHongKim98
         */
        @JvmStatic
        protected fun generateId(): Long = TsidGenerator.generateToLong()
    }
}
