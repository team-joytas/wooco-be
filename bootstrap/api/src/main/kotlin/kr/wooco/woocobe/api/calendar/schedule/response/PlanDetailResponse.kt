package kr.wooco.woocobe.api.calendar.schedule.response

import kr.wooco.woocobe.core.calendar.schedule.application.port.`in`.results.PlanResult

data class PlanDetailResponse(
    val planId: Long,
    val title: String,
    val visitDate: String,
    val group: GroupDetailResponse,
    val places: List<PlaceDetailResponse>,
) {
    data class GroupDetailResponse(
        val groupId: Long,
        val name: String,
        val groupSize: Int,
        val users: List<GroupUserResponse>
    ) {
        data class GroupUserResponse(
            val userId: Long,
            val name: String,
            val profileUrl: String,
        )
    }

    data class PlaceDetailResponse(
        val order: Int,
        val id: Long,
        val name: String,
        val latitude: Double,
        val longitude: Double,
        val address: String,
        val thumbnailUrl: String,
        val kakaoPlaceId: String,
        val averageRating: Double,
        val reviewCount: Long,
    )

    companion object {
        fun from(result: PlanResult): PlanDetailResponse {
            val group = result.group

            return PlanDetailResponse(
                planId = result.id,
                title = result.title,
                visitDate = result.visitDate.toString(),
                group = GroupDetailResponse(
                    groupId = group.id,
                    name = group.name,
                    groupSize = group.groupSize,
                    users = group.users.map { user ->
                        GroupDetailResponse.GroupUserResponse(
                            userId = user.userId,
                            name = user.name,
                            profileUrl = user.profileUrl,
                        )
                    }
                ),
                places = result.places.map {
                    PlaceDetailResponse(
                        order = it.order,
                        id = it.id,
                        name = it.name,
                        latitude = it.latitude,
                        longitude = it.longitude,
                        address = it.address,
                        thumbnailUrl = it.thumbnailUrl,
                        kakaoPlaceId = it.kakaoPlaceId,
                        averageRating = it.averageRating,
                        reviewCount = it.reviewCount,
                    )
                }
            )
        }
    }
}
