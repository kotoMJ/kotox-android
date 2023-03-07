package cz.kotox.android.poeditor

fun getModuleName(originalNameAttribute: String) = when {

    listOf(
        "addWallet",
        "welcome.",
        "intro.text",
        "desktopConnect.title",
        "emailConnect",
        "phoneWallet.b",
        "phoneWallet.e",
        "phoneWallet.t",
        "phoneWallet.w",
        "general.sendingCode",
        "cancelSetup",
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
        "verificationStatus.title."
    ).any { originalNameAttribute.startsWith(it) } -> "doorkeeper"

    listOf(
        "appStatus.",
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
        "general.error.invalid",
        "general.button."
    ).any { originalNameAttribute.startsWith(it) } -> "core"

    else -> ""
}