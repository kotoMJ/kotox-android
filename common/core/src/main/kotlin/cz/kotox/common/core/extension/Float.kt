package cz.kotox.common.core.extension

import kotlin.math.abs
import kotlin.math.roundToInt

/**/

fun Float.isWholeNumber() = abs(this) % 1.0 < 1e-10

fun Float.roundOneDecimal(): Float = ((this * 10.0).roundToInt() / 10.0).toFloat()
fun Float.roundTwoDecimals(): Float = ((this * 100.0).roundToInt() / 100.0).toFloat()