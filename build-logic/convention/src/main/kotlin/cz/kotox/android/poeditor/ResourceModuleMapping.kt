package cz.kotox.android.poeditor


data class TargetResource(
    val moduleName: String,
    val resourceName: String
)

val defaultTargetResource = TargetResource(
    moduleName = "kotox-mobile-playground",
    resourceName = "not_sorted"
)

fun getTargetResource(originalNameAttribute: String): TargetResource = when {

    listOf(

        "lock.screen.biometric.title",
        "lock.screen.biometric.description",
        "lock.screen.biometric.cancel",
        "lock.screen.biometrics.error",
    ).any { originalNameAttribute.startsWith(it) } -> TargetResource(
        moduleName = "kotox-mobile-playground",
        resourceName = "core_authentication"
    )

    listOf(
        "chain.",
    ).any { originalNameAttribute.startsWith(it) } -> TargetResource(
        moduleName = "kotox-mobile-playground",
        resourceName = "core_wallet"
    )

    listOf(
        "verification.button.",
        "verification.error.",
        "verification.title.a",
        "verification.title.c",
        "verification.title.e",
        "verification.title.i",
        "verification.title.r",
        "verification.title.u",
        "verificationStatus.title.availableTicket",
    ).any { originalNameAttribute.startsWith(it) } -> TargetResource(
        moduleName = "kotox-mobile-playground",
        resourceName = "core_verification"
    )

    listOf(
        "qr.",
    ).any { originalNameAttribute.startsWith(it) } -> TargetResource(
        moduleName = "kotox-mobile-playground",
        resourceName = "core_qr"
    )

    listOf(
        "passes.title.permanent",
        "ticket.",
    ).any { originalNameAttribute.startsWith(it) } -> TargetResource(
        moduleName = "kotox-mobile-playground",
        resourceName = "core_ticket"
    )

    listOf(
        "phoneWallet.c",
    ).any { originalNameAttribute.startsWith(it) } -> TargetResource(
        moduleName = "kotox-mobile-playground",
        resourceName = "core_phone"
    )

    listOf(
        "passes.title.pastEvent",
    ).any { originalNameAttribute.startsWith(it) } -> TargetResource(
        moduleName = "kotox-mobile-playground",
        resourceName = "core_event"
    )

    listOf(
        "appStatus.title.inMaintenance",
        "appStatus.message.inMaintenance",
        "general.error.",
        "general.button.",
        "general.or",
        "general.amp",
        "deleteAccount.",
    ).any { originalNameAttribute.startsWith(it) } -> TargetResource(
        moduleName = "kotox-mobile-playground",
        resourceName = "core"
    )

    listOf(
        "login.title",
        "pinEnter.",
        "forgottenPasscode.",
        "lock.screen.biometrics.invalidated",
    ).any { originalNameAttribute.startsWith(it) } -> TargetResource(
        moduleName = "kotox-mobile-playground",
        resourceName = "screenlock"
    )

    listOf(
        "appStatus.button.update",
        "appStatus.title.update",
        "appStatus.message.update",
    ).any { originalNameAttribute.startsWith(it) } -> TargetResource(
        moduleName = "kotox-mobile-playground",
        resourceName = "update"
    )

    listOf(
        "transfer.",
    ).any { originalNameAttribute.startsWith(it) } -> TargetResource(
        moduleName = "kotox-mobile-playground",
        resourceName = "core_transfer"
    )

    listOf(
        "addWallet",
        "welcome.",
        "intro.text",
        "emailConnect",
        "phoneWallet.b",
        "phoneWallet.e",
        "phoneWallet.t",
        "phoneWallet.w",
        "general.sendingCode",
        "cancelSetup",
        "desktopConnect.",
    ).any { originalNameAttribute.startsWith(it) } -> TargetResource(
        moduleName = "kotox-mobile-playground",
        resourceName = "connect_wallet"
    )


    listOf(
        "verify.",
        "verification.title.verification",
        "verificationStatus.button.",
        "verificationStatus.title.alreadyCheckedIn",
        "verificationStatus.title.scanEnterPass",
        "verificationStatus.title.tier",
        "verificationStatus.title.tokenId",
        "verificationStatus.title.c",
        "verificationStatus.title.e",
        "verificationStatus.title.i",
        "verificationStatus.title.r",
        "verificationStatus.title.u",
        "organizer."
    ).any { originalNameAttribute.startsWith(it) } -> TargetResource(
        moduleName = "kotox-mobile-playground",
        resourceName = "doorkeeper"
    )

    listOf(
        "appStatus.title.noWallets",
        "appStatus.message.noWallets",
        "appStatus.button.connectWallet",
        "passes.button",
        "passes.message",
        "passes.title.a",
        "passes.title.b",
        "passes.title.c",
        "passes.title.d",
        "passes.title.m",
        "passes.title.n",
        "passes.title.o",
        "passes.title.t",
        "passes.title.w",
        "passes.wallet.",
        "passes.subtitle.n",
        "exportKey.",
        "events.eventDetail",
        "settings.",
        "enterPass.",
        "myWallets.",
        "plural.ticketsCount",
        "general.text.version",
    ).any { originalNameAttribute.startsWith(it) } -> TargetResource(
        moduleName = "kotox-mobile-playground",
        resourceName = "main"
    )


    else -> defaultTargetResource
}