package kr.wooco.woocobe.fcm.common.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.ThreadManager
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import java.io.ByteArrayInputStream
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory

@Configuration
@ComponentScan(basePackages = ["kr.wooco.woocobe.fcm"])
@ConfigurationPropertiesScan(basePackages = ["kr.wooco.woocobe.fcm"])
class FcmConfig(
    @param:Value("\${fcm.account.credentials}") private val fcmAccountCredentials: String,
) {
    @Bean
    fun firebaseApp(): FirebaseApp {
        val inputStream = ByteArrayInputStream(fcmAccountCredentials.toByteArray(Charsets.UTF_8))
        val options = FirebaseOptions
            .builder()
            .setCredentials(GoogleCredentials.fromStream(inputStream))
            .setThreadManager(VirtualThreadManager())
            .build()
        return FirebaseApp.initializeApp(options)
    }

    @Bean
    fun firebaseMessaging(firebaseApp: FirebaseApp): FirebaseMessaging = FirebaseMessaging.getInstance(firebaseApp)

    class VirtualThreadManager : ThreadManager() {
        override fun getExecutor(app: FirebaseApp): ExecutorService = Executors.newVirtualThreadPerTaskExecutor()

        override fun releaseExecutor(
            app: FirebaseApp,
            executor: ExecutorService,
        ) {
            executor.shutdown()
        }

        override fun getThreadFactory(): ThreadFactory = Thread.ofVirtual().name("firebase-vt-", 0).factory()
    }
}
