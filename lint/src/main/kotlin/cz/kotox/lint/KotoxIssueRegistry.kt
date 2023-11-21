package cz.kotox.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API

class KotoxIssueRegistry : IssueRegistry() {

    override val issues = listOf(
        RawDispatchersUsageDetector.ISSUE,
        MissingResourceImportAliasDetector.ISSUE,
    )

    override val api: Int = CURRENT_API

    override val minApi: Int = 12

    override val vendor = Vendor(
        feedbackUrl = "https://kotox.cz",
        identifier = "kotox.cz",
        vendorName = "KOTOX",
    )
}
