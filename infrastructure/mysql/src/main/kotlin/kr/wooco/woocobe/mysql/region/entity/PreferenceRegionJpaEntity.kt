package kr.wooco.woocobe.mysql.region.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.mysql.common.entity.BaseEntity
import kr.wooco.woocobe.mysql.common.utils.Tsid

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
