package kr.wooco.woocobe.core.region.application.port.`in`

fun interface DeletePreferenceRegionUseCase {
    data class Command(
        val userId: Long,
        val preferenceRegionId: Long,
    )

    fun deletePreferenceRegion(command: Command)
}
