package kr.wooco.woocobe.common.infrastructure.storage

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Deprecated(
    message = "entity 패키지내에 정의된 BaseTimeEntity로 변경해주세요.",
    replaceWith = ReplaceWith(
        "BaseTimeEntity",
        "kr.wooco.woocowe.common.infrastructure.storage.entity.BaseTimeEntity",
    ),
)
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity {
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
}
