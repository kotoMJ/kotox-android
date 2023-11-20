package cz.kotox.konsist

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withAnnotationOf
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.Test
import androidx.compose.ui.tooling.preview.Preview

// Check Android specific coding rules.
class AndroidComposeKonsistTest {
    //FIXME MJ - All occurrences suppressed by @Suppress("All JetPack Compose previews contain 'Preview' in method name") temporarily
    @Test
    fun `All JetPack Compose previews contain 'Preview' in method name`() {
        Konsist
            .scopeFromProject()
            .functions()
            .withAnnotationOf(Preview::class)
            .assertTrue {
                it.name.contains("Preview")
            }
    }
}
