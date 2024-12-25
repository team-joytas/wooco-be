package kr.wooco.woocobe.auth.infrastructure.storage

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.auth.domain.model.AuthUser
import kr.wooco.woocobe.auth.domain.model.SocialAuthInfo
import kr.wooco.woocobe.auth.domain.model.SocialAuthType
import kr.wooco.woocobe.common.infrastructure.storage.IdGenerator
import kr.wooco.woocobe.common.storage.BaseTimeEntity

@Entity
@Table(name = "auth_users")
class AuthUserEntity(
    @Enumerated(EnumType.STRING)
    @Column(name = "social_type")
    val socialType: SocialAuthType,
    @Column(name = "social_id")
    val socialId: String,
    @Column(name = "user_id")
    val userId: Long,
    @Id
    @Column(name = "auth_user_id")
    val id: Long,
) : BaseTimeEntity() {
    fun toDomain(): AuthUser =
        AuthUser(
            id = id,
            userId = userId,
            socialAuthInfo = SocialAuthInfo(
                socialId = socialId,
                socialType = socialType,
            ),
        )

    companion object {
        fun from(authUser: AuthUser): AuthUserEntity =
            with(authUser) {
                AuthUserEntity(
                    id = IdGenerator.generateId(),
                    userId = userId,
                    socialId = socialAuthInfo.socialId,
                    socialType = socialAuthInfo.socialType,
                )
            }
    }
}
