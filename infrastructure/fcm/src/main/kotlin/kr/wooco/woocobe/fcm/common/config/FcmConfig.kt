package kr.wooco.woocobe.fcm.common.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import java.io.ByteArrayInputStream

@Configuration
@ComponentScan(basePackages = ["kr.wooco.woocobe.fcm"])
@ConfigurationPropertiesScan(basePackages = ["kr.wooco.woocobe.fcm"])
class FcmConfig(
    @Value("\${fcm.account.credentials}") private val fcmAccountCredentials: String,
) {
    @Bean
    fun firebaseApp(): FirebaseApp {
        val inputStream = ByteArrayInputStream(fcmAccountCredentials.toByteArray(Charsets.UTF_8))
        val options = FirebaseOptions
            .builder()
            .setCredentials(GoogleCredentials.fromStream(inputStream))
            .build()
        return FirebaseApp.initializeApp(options)
    }

    @Bean
    fun firebaseMessaging(firebaseApp: FirebaseApp): FirebaseMessaging = FirebaseMessaging.getInstance(firebaseApp)
}
