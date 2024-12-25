package kr.wooco.woocobe.user.infrastructure.storage

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.common.infrastructure.storage.IdGenerator
import kr.wooco.woocobe.common.storage.BaseTimeEntity
import kr.wooco.woocobe.user.domain.model.User

@Entity
@Table(name = "users")
class UserEntity(
    @Column(name = "profile_url")
    val profileUrl: String,
    @Column(name = "name")
    val name: String,
    @Id
    @Column(name = "user_id")
    val id: Long,
) : BaseTimeEntity() {
    fun toDomain(): User =
        User(
            id = id,
            name = name,
            profileUrl = profileUrl,
        )

    // TODO: 리팩토링 (from 과 fromWithId)
    companion object {
        fun from(user: User): UserEntity =
            with(user) {
                UserEntity(
                    id = IdGenerator.generateId(),
                    name = name,
                    profileUrl = profileUrl,
                )
            }

        fun fromWithId(user: User): UserEntity =
            with(user) {
                UserEntity(
                    id = id,
                    name = name,
                    profileUrl = profileUrl,
                )
            }
    }
}
