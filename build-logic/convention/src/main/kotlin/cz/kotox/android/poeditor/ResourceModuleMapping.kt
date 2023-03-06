package cz.kotox.android.poeditor

fun getModuleName(originalNameAttibute: String) = when {
    originalNameAttibute.startsWith("addWallet") -> "connect_wallet"
    originalNameAttibute.startsWith("welcome.") -> "connect_wallet"
    originalNameAttibute.startsWith("intro.text") -> "connect_wallet"
    originalNameAttibute.startsWith("desktopConnect.title") -> "connect_wallet"
    originalNameAttibute.startsWith("emailConnect") -> "connect_wallet"
    originalNameAttibute.startsWith("phoneWallet") -> "connect_wallet"
    originalNameAttibute.startsWith("general.sendingCode") -> "connect_wallet"
    originalNameAttibute.startsWith("cancelSetup") -> "connect_wallet"

    originalNameAttibute.startsWith("login.title") -> "authentication"
    originalNameAttibute.startsWith("pinEnter.") -> "authentication"
    originalNameAttibute.startsWith("forgottenPasscode.") -> "authentication"

    originalNameAttibute.startsWith("verify.") -> "doorkeeper"
    originalNameAttibute.startsWith("verification.") -> "doorkeeper"
    else -> ""
}