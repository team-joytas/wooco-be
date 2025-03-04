package kr.wooco.woocobe.rest.place

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("app.google.place")
data class GooglePlaceProperties(
    val apiKey: String,
)
