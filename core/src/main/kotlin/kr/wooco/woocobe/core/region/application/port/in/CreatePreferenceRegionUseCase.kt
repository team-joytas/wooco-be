package kr.wooco.woocobe.core.region.application.port.`in`

fun interface CreatePreferenceRegionUseCase {
    data class Command(
        val userId: Long,
        val primaryRegion: String,
        val secondaryRegion: String,
    )

    fun createPreferenceRegion(command: Command): Long
}
