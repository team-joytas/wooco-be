package kr.wooco.woocobe.region.application.command.port.`in`

data class DeletePreferenceRegionCommand(
    val userId: Long,
    val preferenceRegionId: Long,
)

fun interface DeletePreferenceRegionUseCase {
    fun deletePreferenceRegion(command: DeletePreferenceRegionCommand)
}
