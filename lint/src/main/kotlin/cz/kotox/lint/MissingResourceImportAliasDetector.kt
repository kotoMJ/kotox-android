package cz.kotox.lint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.android.tools.lint.detector.api.TextFormat
import cz.kotox.lint.extension.isKotlin
import java.util.EnumSet
import org.jetbrains.kotlin.psi.KtImportDirective
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UImportStatement
import org.jetbrains.uast.getContainingUFile

/**
 * Reports an error when an R class, other than the local module's,
 * is imported without an import alias.
 */
class MissingResourceImportAliasDetector : Detector(), SourceCodeScanner {

    companion object {
        private val SCOPES = Implementation(
            /* detectorClass = */ MissingResourceImportAliasDetector::class.java,
            /* scope = */ EnumSet.of(Scope.JAVA_FILE),
        )

        val ISSUE = Issue.create(
            id = "MissingResourceImportAlias",
            briefDescription = "Missing import alias for R class",
            explanation = """
                      Only the local module's R class is allowed to be imported without an alias. \
                      Add an import alias for this. For example, import core.resources.R as ResourcesR
                      """,
            category = Category.CORRECTNESS,
            priority = 6,
            severity = Severity.FATAL,
            implementation = SCOPES,
        )
    }

    override fun getApplicableUastTypes(): List<Class<out UElement>> = listOf(
        UImportStatement::class.java,
    )

    override fun createUastHandler(context: JavaContext): UElementHandler? {
        if (context.isKotlin.not()) return null

        return object : UElementHandler() {
            override fun visitImportStatement(node: UImportStatement) {
                val importDirective = node.sourcePsi as? KtImportDirective ?: return

                if (importDirective.importedName?.identifier == "R" && importDirective.aliasName == null) {
                    val packageName = context.project.`package`
                    val filePackageName = node.getContainingUFile()?.packageName

                    val importedFqName = importDirective.importedFqName
                    val parentImportedFqName = importedFqName?.parent()?.asString()

                    val isLocalResourceImport = parentImportedFqName?.let {
                        if (packageName != null) {
                            it == packageName
                        } else {
                            filePackageName?.startsWith(it)
                        }
                    } ?: true

                    if (!isLocalResourceImport) {
                        context.report(importDirective)
                    }
                }
            }
        }
    }

    private fun JavaContext.report(importDirective: KtImportDirective) = report(
        issue = ISSUE,
        location = getNameLocation(importDirective),
        message = ISSUE.getBriefDescription(TextFormat.TEXT),
    )
}
