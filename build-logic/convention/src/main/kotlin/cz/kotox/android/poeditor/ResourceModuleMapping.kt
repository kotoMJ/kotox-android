package cz.kotox.android.poeditor

fun getModuleName(originalNameAttribute: String) = when {

    listOf(
        "addWallet",
        "welcome.",
        "intro.text",
        "desktopConnect.title",
        "emailConnect",
        "phoneWallet",
        "general.sendingCode",
        "cancelSetup"
    ).any { originalNameAttribute.startsWith(it) } -> "connect_wallet"


    listOf(
        "login.title",
        "pinEnter.",
        "forgottenPasscode."
    ).any { originalNameAttribute.startsWith(it) } -> "authentication"

    listOf(
        "verify.",
        "verification."
    ).any { originalNameAttribute.startsWith(it) } -> "doorkeeper"
    else -> ""
}