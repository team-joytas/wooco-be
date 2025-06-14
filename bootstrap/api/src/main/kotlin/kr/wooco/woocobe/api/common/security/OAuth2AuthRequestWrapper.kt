package kr.wooco.woocobe.api.common.security

import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest

data class OAuth2AuthRequestWrapper(
    val authorizationUri: String,
    val clientId: String,
    val redirectUri: String,
    val scopes: Set<String>,
    val state: String,
    val attributes: Map<String, Any>,
) {
    fun toOAuth2AuthorizationRequest(): OAuth2AuthorizationRequest =
        OAuth2AuthorizationRequest
            .authorizationCode()
            .authorizationUri(authorizationUri)
            .clientId(clientId)
            .redirectUri(redirectUri)
            .scopes(scopes)
            .state(state)
            .attributes(attributes)
            .build()

    companion object {
        fun from(request: OAuth2AuthorizationRequest) =
            OAuth2AuthRequestWrapper(
                authorizationUri = request.authorizationUri,
                clientId = request.clientId,
                redirectUri = request.redirectUri,
                scopes = request.scopes,
                state = request.state,
                attributes = request.attributes,
            )
    }
}
