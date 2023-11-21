package cz.kotox.konsist

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assert
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.Test

class UseCaseKonsistTest {

    @Test
    fun `classes with 'UseCase' suffix should reside in 'domain' and 'usecase' packages`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameEndingWith("UseCase")
            .assertTrue { it.resideInPackage("..domain..usecase..") }
    }

    @Test
    fun `every use case constructor has alphabetically ordered parameters`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameEndingWith("UseCase")
            .flatMap { it.constructors }
            .assertTrue {
                val names = it.parameters.map { parameter -> parameter.name }
                val sortedNames = names.sorted()
                names == sortedNames
            }
    }

    //FIXME MJ - not fulfilled in the project yet.
    //@Test
    fun `classes with 'UseCase' suffix should have single public operator method named 'invoke'`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameEndingWith("UseCase")
            .assertTrue {
                val hasSingleInvokeOperatorMethod = it.containsFunction { function ->
                    function.name == "invoke" && function.hasPublicOrDefaultModifier && function.hasOperatorModifier
                }

                hasSingleInvokeOperatorMethod && it.numPublicOrDefaultDeclarations() == 1
            }
    }
}