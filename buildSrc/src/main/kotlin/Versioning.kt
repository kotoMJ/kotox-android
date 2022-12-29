object Versioning {


    const val VERSION_CODE = Config.VERSION_MAJOR * 10000000 + Config.VERSION_MINOR * 100000 + Config.VERSION_PATCH * 1000 + Config.VERSION_BUILD
    const val VERSION_NAME = "${Config.VERSION_MAJOR}.${Config.VERSION_MINOR}.${Config.VERSION_PATCH}"
}
