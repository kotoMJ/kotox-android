package cz.kotox.konsist

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.declaration.KoFunctionDeclaration
import com.lemonappdev.konsist.api.declaration.KoPropertyDeclaration
import com.lemonappdev.konsist.api.ext.list.indexOfFirstInstance
import com.lemonappdev.konsist.api.ext.list.indexOfLastInstance
import com.lemonappdev.konsist.api.ext.list.properties
import com.lemonappdev.konsist.api.verify.assertFalse
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.Test

// Check General coding rules.
class GeneralKonsistTest {

    @Test
    fun `properties are declared before functions`() {
        Konsist
            .scopeFromProject()
            .classes()
            .assertTrue {
                val lastKoPropertyDeclarationIndex = it
                    .declarations(includeNested = false, includeLocal = false)
                    .indexOfLastInstance<KoPropertyDeclaration>()

                val firstKoFunctionDeclarationIndex = it
                    .declarations(includeNested = false, includeLocal = false)
                    .indexOfFirstInstance<KoFunctionDeclaration>()

                if (lastKoPropertyDeclarationIndex != -1 && firstKoFunctionDeclarationIndex != -1) {
                    lastKoPropertyDeclarationIndex < firstKoFunctionDeclarationIndex
                } else {
                    true
                }
            }
    }
    @Test
    fun `package name must match file path`() {
        Konsist.scopeFromProject()
            .packages
            .assertTrue { it.hasMatchingPath }
    }

    @Test
    fun `no field should have 'm' prefix`() {
        Konsist.scopeFromProject()
            .classes()
            .properties()
            .assertFalse {
                val secondCharacterIsUppercase = it.name.getOrNull(1)?.isUpperCase() ?: false
                it.name.startsWith('m') && secondCharacterIsUppercase
            }
    }

    @Test
    fun `no class should use Android util logging`() {
        Konsist.scopeFromProject()
            .files
            .assertFalse { it.hasImportWithName("android.util.Log") }
    }

    @Test
    fun `no empty files allowed`() {
        Konsist.scopeFromProject()
            .files
            .assertFalse { it.text.isEmpty() }
    }

    //FIXME MJ - not fulfilled in the project yet.
//    @Test
//    fun `companion object is last declaration in the class`() {
//        Konsist
//            .scopeFromProject()
//            .classes()
//            .assertTrue {
//                val companionObject = it.objects(includeNested = false).lastOrNull { obj ->
//                    obj.hasModifier(KoModifier.COMPANION)
//                }
//
//                if (companionObject != null) {
//                    it.declarations(includeNested = false, includeLocal = false).last() == companionObject
//                } else {
//                    true
//                }
//            }
//    }

    //FIXME MJ - add dependency on Inject
//    @Test
//    fun `no class should use field injection`() {
//        Konsist
//            .scopeFromProject()
//            .classes()
//            .properties()
//            .assertFalse { it.hasAnnotationOf<Inject>() }
//    }

    @Test
    fun `no wildcard imports allowed`() {
        Konsist
            .scopeFromProject()
            .imports
            .assertFalse { it.isWildcard }
    }
}
