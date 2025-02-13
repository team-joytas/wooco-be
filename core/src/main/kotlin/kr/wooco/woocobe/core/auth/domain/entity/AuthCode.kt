package kr.wooco.woocobe.core.auth.domain.entity

import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64
import java.util.UUID

data class AuthCode(
    val id: String,
    val verifier: String,
    val challenge: String,
) {
    companion object {
        private const val ALGORITHM = "SHA-256"

        fun create(): AuthCode {
            val verifier = generateVerifier()
            val challenge = generateChallenge(verifier)
            return AuthCode(
                id = generateId(),
                verifier = verifier,
                challenge = challenge,
            )
        }

        private fun generateId(): String = UUID.randomUUID().toString()

        private fun generateVerifier(): String {
            val bytes = ByteArray(64).apply {
                SecureRandom().nextBytes(this)
            }
            return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes)
        }

        private fun generateChallenge(verifier: String): String {
            val digest = MessageDigest.getInstance(ALGORITHM)
            val hashed = digest.digest(verifier.toByteArray())
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hashed)
        }
    }
}
