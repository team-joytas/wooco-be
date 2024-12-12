package kr.wooco.woocobe.user.infrastructure.storage

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.wooco.woocobe.common.storage.BaseTimeEntity
import kr.wooco.woocobe.user.domain.model.User

@Entity
@Table(name = "users")
class UserEntity(
    val name: String,
    @Id
    @Column(name = "user_id")
    val id: Long,
) : BaseTimeEntity() {
    fun toDomain(): User =
        User(
            id = id,
            name = name,
        )

    companion object {
        fun from(user: User): UserEntity =
            with(user) {
                UserEntity(
                    id = id,
                    name = name,
                )
            }
    }
}
