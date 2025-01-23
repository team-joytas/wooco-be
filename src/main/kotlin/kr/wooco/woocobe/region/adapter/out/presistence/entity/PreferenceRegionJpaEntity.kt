package kr.wooco.woocobe.region.adapter.out.presistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.common.infrastructure.storage.Tsid
import kr.wooco.woocobe.common.infrastructure.storage.entity.BaseEntity

@Entity
@Table(name = "preference_regions")
class PreferenceRegionJpaEntity(
    @Column(name = "primary_region")
    val primaryRegion: String,
    @Column(name = "secondary_region")
    val secondaryRegion: String,
    @Column(name = "user_id")
    val userId: Long,
    @Id @Tsid
    @Column(name = "region_id")
    override val id: Long,
) : BaseEntity()
