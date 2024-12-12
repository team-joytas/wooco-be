package kr.wooco.woocobe.support

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.extensions.spring.SpringAutowireConstructorExtension

object KotestConfig : AbstractProjectConfig() {
    override fun extensions() = listOf(SpringAutowireConstructorExtension)
}
