package kr.wooco.woocobe.auth.domain.model

import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64

class Pkce(
    val verifier: String,
    val challenge: String,
) {
    companion object {
        private const val ALGORITHM = "SHA-256"

        fun register(): Pkce {
            val verifier = generateVerifier()
            val challenge = generateChallenge(verifier)
            return Pkce(
                verifier = verifier,
                challenge = challenge,
            )
        }

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
