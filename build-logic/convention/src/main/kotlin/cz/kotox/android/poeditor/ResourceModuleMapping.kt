package cz.kotox.android.poeditor


data class TargetResource(
    val moduleName: String,
    val resourceName: String
)

val defaultTargetResource = TargetResource(
    "kotox-mobile-playground",
    ""
)
fun getTargetResource(originalNameAttribute: String): TargetResource = when {

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
        "desktopConnect."
    ).any { originalNameAttribute.startsWith(it) } -> TargetResource(
        "kotox-mobile-media",
        "connect_wallet"
    )


    listOf(
        "login.title",
        "pinEnter.",
        "forgottenPasscode.",
    ).any { originalNameAttribute.startsWith(it) } -> TargetResource(
        "kotox-mobile-playground",
        "authentication"
    )

    listOf(
        "verify.",
        "verification.title.verification",
        "verificationStatus.button.",
        "verificationStatus.title.",
        "organizer."
    ).any { originalNameAttribute.startsWith(it) } -> TargetResource(
        "kotox-mobile-playground",
        "doorkeeper"
    )

    listOf(
        "appStatus.title.noWallets",
        "appStatus.message.noWallets",
        "passes.button",
        "passes.message",
        "passes.title.a",
        "passes.title.b",
        "passes.title.c",
        "passes.title.d",
        "passes.title.m",
        "passes.title.n",
        "passes.title.o",
        "passes.title.pastEvent",
        "passes.title.t",
        "exportKey.",
        "events.eventDetail",
        "settings.",
        "enterPass.",
        "myWallets.",
        "plural.ticketsCount",
    ).any { originalNameAttribute.startsWith(it) } -> TargetResource(
        "kotox-mobile-playground",
        "main"
    )

    listOf(
        "verification.",
    ).any { originalNameAttribute.startsWith(it) } -> TargetResource(
        "kotox-mobile-playground",
        "core_verification"
    )

    listOf(
        "general.button.cancel",
        "general.button.close",
    ).any { originalNameAttribute.startsWith(it) } -> TargetResource(
        "kotox-mobile-playground",
        "core_ui"
    )

    listOf(
        "passes.title.permanent",
    ).any { originalNameAttribute.startsWith(it) } -> TargetResource(
        "kotox-mobile-playground",
        "core_ticket"
    )

    listOf(
        "phoneWallet.c",
    ).any { originalNameAttribute.startsWith(it) } -> TargetResource(
        "kotox-mobile-playground",
        "core_phone"
    )

    listOf(
        "appStatus.title.inMaintenance",
        "general.error.",
        "general.button.",
        "deleteAccount.",
    ).any { originalNameAttribute.startsWith(it) } -> TargetResource(
        "kotox-mobile-playground",
        "core"
    )

    listOf(
        "appStatus.button.update",
        "appStatus.title.update",
        "appStatus.message.update",
    ).any { originalNameAttribute.startsWith(it) } -> TargetResource(
        "kotox-mobile-playground",
        "update"
    )

    listOf(
        "appStatus.message.inMaintenance",
    ).any { originalNameAttribute.startsWith(it) } -> TargetResource(
        "kotox-mobile-playground",
        "maintenance"
    )

    listOf(
        "transfer.",
    ).any { originalNameAttribute.startsWith(it) } -> TargetResource(
        "kotox-mobile-playground",
        "transfer"
    )

    else -> defaultTargetResource
}