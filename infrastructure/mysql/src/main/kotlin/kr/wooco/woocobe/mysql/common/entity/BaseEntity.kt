package kr.wooco.woocobe.mysql.common.entity

import jakarta.persistence.MappedSuperclass
import org.hibernate.proxy.HibernateProxy

@MappedSuperclass
abstract class BaseEntity {
    abstract val id: Long

// FIXME & NOTE : id 생성 로직이 모두 도메인 엔티티측으로 위임되면 아래 주석 풀어주십쇼!!
// : Persistable<Long> {

//    @Suppress("ktlint:standard:backing-property-naming")
//    @Transient
//    private var _isNew: Boolean = true

//    override fun getId(): Long = id

//    override fun isNew(): Boolean = _isNew

//    @PostLoad
//    @PostPersist
//    protected fun markNotNew() {
//        _isNew = false
//    }

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
