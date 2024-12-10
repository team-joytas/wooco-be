package kr.wooco.woocobe.common.domain

fun interface UseCase<I, O> {

    fun execute(input: I): O
}
