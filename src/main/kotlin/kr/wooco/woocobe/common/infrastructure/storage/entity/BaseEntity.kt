package kr.wooco.woocobe.common.infrastructure.storage.entity

import jakarta.persistence.MappedSuperclass
import org.hibernate.proxy.HibernateProxy
import org.springframework.data.domain.Persistable

@MappedSuperclass
abstract class BaseEntity : Persistable<Long> {
    abstract val id: Long

    override fun getId(): Long = id

    override fun isNew(): Boolean = id == 0L

    override fun equals(other: Any?): Boolean {
        if (other == null) return false

        if (other !is HibernateProxy && this::class != other::class) return false

        return when (other) {
            is HibernateProxy -> id == other.hibernateLazyInitializer.identifier
            is BaseEntity -> id == other.id
            else -> false
        }
    }

    override fun hashCode() = id.hashCode()
}
