data class Versions(
    val major: Int, // max 2 digits
    val minor: Int, // max 2 digits
    val patch: Int, // max 2 digits
    val build: Int, // max 3 digits
) {
    val versionCode = major * 10000000 + minor * 100000 + patch * 1000 + build
    val versionName = "$major.$minor.$patch.$build"
}
