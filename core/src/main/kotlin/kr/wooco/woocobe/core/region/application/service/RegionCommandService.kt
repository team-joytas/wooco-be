package kr.wooco.woocobe.core.region.application.service

import kr.wooco.woocobe.core.region.application.port.`in`.AddPreferenceRegionCommand
import kr.wooco.woocobe.core.region.application.port.`in`.AddPreferenceRegionUseCase
import kr.wooco.woocobe.core.region.application.port.`in`.DeletePreferenceRegionCommand
import kr.wooco.woocobe.core.region.application.port.`in`.DeletePreferenceRegionUseCase
import kr.wooco.woocobe.core.region.application.port.out.DeletePreferenceRegionPort
import kr.wooco.woocobe.core.region.application.port.out.LoadPreferenceRegionPort
import kr.wooco.woocobe.core.region.application.port.out.SavePreferenceRegionPort
import kr.wooco.woocobe.core.region.domain.exception.AlreadyPreferenceRegionException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class RegionCommandService(
    private val loadPreferenceRegionPort: LoadPreferenceRegionPort,
    private val savePreferenceRegionPort: SavePreferenceRegionPort,
    private val deletePreferenceRegionPort: DeletePreferenceRegionPort,
) : AddPreferenceRegionUseCase,
    DeletePreferenceRegionUseCase {
    @Transactional
    override fun addPreferenceRegion(command: AddPreferenceRegionCommand): Long {
        val isExists = loadPreferenceRegionPort.existsByUserIdAndRegion(
            userId = command.userId,
            primaryRegion = command.primaryRegion,
            secondaryRegion = command.secondaryRegion,
        )
        if (isExists) throw AlreadyPreferenceRegionException
        val preferenceRegion = savePreferenceRegionPort.save(command.toPreferenceRegion())
        return preferenceRegion.id
    }

    @Transactional
    override fun deletePreferenceRegion(command: DeletePreferenceRegionCommand) {
        val preferenceRegion = loadPreferenceRegionPort.getByUserIdAndPreferenceRegionId(
            userId = command.userId,
            preferenceRegionId = command.preferenceRegionId,
        )
        deletePreferenceRegionPort.deleteByPreferenceRegionId(preferenceRegion.id)
    }
}
