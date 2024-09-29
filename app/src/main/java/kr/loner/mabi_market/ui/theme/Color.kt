package kr.loner.mabi_market.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb


val BLUE05 = Color(0xFF0071AC)
val BLUE04 = Color(0xFF1991CF)
val BLUE03 = Color(0xFF5ABDED)
val BLUE02 = Color(0xFFB7ECFF)
val BLUE01 = Color(0xFFD6F4FF)

val GRAY01 = Color(0xFF797979)
val GRAY02 = Color(0xFF595959)
val YELLOW01 = Color(0xFFFF7A00)
val RED01 = Color(0xFFFF2400)

fun Color.toAGColor() = toArgb().run { android.graphics.Color.argb(alpha, red, green, blue) }