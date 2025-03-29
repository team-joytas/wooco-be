package kr.wooco.woocobe.common.tsid

import com.github.f4b6a3.tsid.TsidCreator

@Suppress("MemberVisibilityCanBePrivate")
object TsidGenerator {
    /**
     * Long 타입의 TSID를 생성합니다.
     * 시간 순으로 정렬 가능한 고유 식별자를 생성합니다.
     *
     * String 타입으로 생성할 경우 @see [TsidGenerator.generateToString]
     *
     * @return Long 생성한 TSID를 Long 타입으로 표현한 값
     */
    fun generateToLong(): Long = TsidCreator.getTsid().toLong()

    /**
     * String 타입의 TSID를 생성합니다.
     * 시간 순으로 정렬 가능한 고유 식별자를 생성합니다.
     *
     * Long 타입으로 생성할 경우 [TsidGenerator.generateToLong]
     *
     * @return String 생성한 TSID를 Long 타입으로 표현한 값
     */
    fun generateToString(): String = TsidCreator.getTsid().toString()
}
