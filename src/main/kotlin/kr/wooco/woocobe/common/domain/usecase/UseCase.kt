package kr.wooco.woocobe.common.domain.usecase

fun interface UseCase<I, O> {
    fun execute(input: I): O
}
