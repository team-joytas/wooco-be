package kr.wooco.woocobe.api.common.warmup

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Internal Warmup 엔드포인트
 *
 * Spring Security Filter Chain을 warmup하기 위한 내부 전용 엔드포인트입니다.
 * 외부에서 접근할 수 없도록 SecurityIgnorePath에 등록하지 않습니다.
 */
@RestController
@RequestMapping("/internal/warmup")
class WarmupController {
    @GetMapping
    fun warmup(): ResponseEntity<WarmupResponse> =
        ResponseEntity.ok(
            WarmupResponse(
                status = "ok",
                message = "Filter chain warmup endpoint",
            ),
        )
}

data class WarmupResponse(
    val status: String,
    val message: String,
)
