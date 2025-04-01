package kr.wooco.woocobe.core.region.application.port.`in`

data class DeletePreferenceRegionCommand(
    val userId: Long,
    val preferenceRegionId: Long,
)

fun interface DeletePreferenceRegionUseCase {
    fun deletePreferenceRegion(command: DeletePreferenceRegionCommand)
}
