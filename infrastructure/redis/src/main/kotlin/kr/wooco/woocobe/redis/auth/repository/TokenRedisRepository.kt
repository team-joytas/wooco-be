package kr.wooco.woocobe.redis.auth.repository

import kr.wooco.woocobe.redis.auth.entity.TokenRedisEntity
import org.springframework.data.repository.CrudRepository

interface TokenRedisRepository : CrudRepository<TokenRedisEntity, Long>
