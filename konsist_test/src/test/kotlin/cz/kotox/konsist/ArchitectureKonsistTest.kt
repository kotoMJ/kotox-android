package cz.kotox.konsist

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assert
import org.junit.Test

/**
 * Check architecture coding rules.
 */
class ArchitectureKonsistTest {


//TODO MJ - Layer component has a bug in 0.13.0: https://github.com/LemonAppDev/konsist/discussions/765
//    @Test
    fun `clean architecture layers have correct dependencies`() {
        Konsist
            .scopeFromProduction()
            .assertArchitecture {
                // Define layers
                val packagePrefix = "cz.kotox.common.task.poc"
                val domain = Layer("domain", "cz.kotox..domain..")
                val presentation = Layer("ui", "cz.kotox..ui..")
                val data = Layer("data", "cz.kotox..data..")

                // Define architecture assertions
                data.dependsOnNothing()
                domain.dependsOn(data)
                presentation.dependsOn(domain)
            }
    }
}
