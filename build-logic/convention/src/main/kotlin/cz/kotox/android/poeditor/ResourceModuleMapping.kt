package cz.kotox.android.poeditor

fun getModuleName(originalNameAttribute: String) = when {

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
    ).any { originalNameAttribute.startsWith(it) } -> "connect_wallet"


    listOf(
        "login.title",
        "pinEnter.",
        "forgottenPasscode.",
    ).any { originalNameAttribute.startsWith(it) } -> "authentication"

    listOf(
        "verify.",
        "verification.title.verification",
        "verificationStatus.button.",
        "verificationStatus.title.",
    ).any { originalNameAttribute.startsWith(it) } -> "doorkeeper"

    listOf(
        "appStatus.title.noWallets",
        "passes.button",
        "passes.message",
        "passes.title.a",
        "passes.title.b",
        "passes.title.c",
        "passes.title.m",
        "passes.title.n",
        "passes.title.o",
        "passes.title.pastEvent",
        "passes.title.t",
        "exportKey.",
        "events.eventDetail",
        "settings.",
        "enterPass.",
    ).any { originalNameAttribute.startsWith(it) } -> "main"

    listOf(
        "verification.",
    ).any { originalNameAttribute.startsWith(it) } -> "core_verification"

    listOf(
        "general.button.cancel",
        "general.button.close",
    ).any { originalNameAttribute.startsWith(it) } -> "core_ui"

    listOf(
        "passes.title.permanent",
    ).any { originalNameAttribute.startsWith(it) } -> "core_ticket"

    listOf(
        "phoneWallet.c",
    ).any { originalNameAttribute.startsWith(it) } -> "core_phone"

    listOf(
        "appStatus.title.inMaintenance",
        "general.error.",
        "general.button.",
        "deleteAccount.",
    ).any { originalNameAttribute.startsWith(it) } -> "core"

    listOf(
        "appStatus.button.update",
        "appStatus.title.update",
        "appStatus.message.update",
    ).any { originalNameAttribute.startsWith(it) } -> "update"

    listOf(
        "appStatus.message.inMaintenance",
    ).any { originalNameAttribute.startsWith(it) } -> "maintenance"

    listOf(
        "transfer.",
    ).any { originalNameAttribute.startsWith(it) } -> "transfer"

    else -> ""
}