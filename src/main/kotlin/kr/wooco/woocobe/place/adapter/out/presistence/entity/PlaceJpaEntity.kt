package kr.wooco.woocobe.place.adapter.out.presistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.common.infrastructure.storage.Tsid
import kr.wooco.woocobe.common.infrastructure.storage.entity.BaseTimeEntity

@Entity
@Table(name = "places")
class PlaceJpaEntity(
    @Column(name = "phone_number")
    val phoneNumber: String,
    @Column(name = "thumbnail_url")
    val thumbnailUrl: String,
    @Column(name = "review_count")
    val reviewCount: Long,
    @Column(name = "average_rating")
    val averageRating: Double,
    @Column(name = "kakao_map_place_id")
    val kakaoPlaceId: String,
    @Column(name = "longitude")
    val longitude: Double,
    @Column(name = "latitude")
    val latitude: Double,
    @Column(name = "address")
    val address: String,
    @Column(name = "name")
    val name: String,
    @Id @Tsid
    @Column(name = "place_id")
    override val id: Long = 0L,
) : BaseTimeEntity()
