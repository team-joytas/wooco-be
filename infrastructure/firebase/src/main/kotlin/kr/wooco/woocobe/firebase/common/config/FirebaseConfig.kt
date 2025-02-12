package kr.wooco.woocobe.firebase.common.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
@ComponentScan(basePackages = ["kr.wooco.woocobe.firebase"])
@ConfigurationPropertiesScan(basePackages = ["kr.wooco.woocobe.firebase"])
class FirebaseConfig {
    @Value("\${firebase.config.path}")
    lateinit var firebaseConfigPath: String

    @Bean
    fun firebaseApp(): FirebaseApp {
        val resource = ClassPathResource(firebaseConfigPath)
        val serviceAccount = resource.inputStream
        val options = FirebaseOptions
            .builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()
        return FirebaseApp.initializeApp(options)
    }

    @Bean
    fun firebaseMessaging(firebaseApp: FirebaseApp): FirebaseMessaging = FirebaseMessaging.getInstance(firebaseApp)
}
