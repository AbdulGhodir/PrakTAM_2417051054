package com.example.praktam2_2417051054.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.praktam2_2417051054.R

val Poppins = FontFamily(
    Font(R.font.poppins_thin, FontWeight.Thin),
    Font(R.font.poppins_thinitalic, FontWeight.Thin, FontStyle.Italic),

    Font(R.font.poppins_extralight, FontWeight.ExtraLight),
    Font(R.font.poppins_extralightitalic, FontWeight.ExtraLight, FontStyle.Italic),

    Font(R.font.poppins_light, FontWeight.Light),
    Font(R.font.poppins_lightitalic, FontWeight.Light, FontStyle.Italic),

    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_italic, FontWeight.Normal, FontStyle.Italic),

    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_mediumitalic, FontWeight.Medium, FontStyle.Italic),

    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),

    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_bolditalic, FontWeight.Bold, FontStyle.Italic),

    Font(R.font.poppins_extrabold, FontWeight.ExtraBold),
    Font(R.font.poppins_extrabolditalic, FontWeight.ExtraBold, FontStyle.Italic),

    Font(R.font.poppins_black, FontWeight.Black),
    Font(R.font.poppins_blackitalic, FontWeight.Black, FontStyle.Italic)
)

val Typography = Typography(
    displayLarge = TextStyle(fontFamily = Poppins, fontSize = 30.sp, fontWeight = FontWeight.Bold),
    headlineLarge = TextStyle(fontFamily = Poppins, fontSize = 26.sp, fontWeight = FontWeight.Bold),
    headlineMedium = TextStyle(fontFamily = Poppins, fontSize = 24.sp, fontWeight = FontWeight.Bold),
    titleLarge = TextStyle(fontFamily = Poppins, fontSize = 20.sp, fontWeight = FontWeight.Bold),
    titleMedium = TextStyle(fontFamily = Poppins, fontSize = 18.sp, fontWeight = FontWeight.Bold),
    titleSmall = TextStyle(fontFamily = Poppins, fontSize = 18.sp, fontWeight = FontWeight.Medium),
    bodyLarge = TextStyle(fontFamily = Poppins, fontSize = 16.sp, fontWeight = FontWeight.Bold),
    bodyMedium = TextStyle(fontFamily = Poppins, fontSize = 16.sp, fontWeight = FontWeight.Medium),
    bodySmall = TextStyle(fontFamily = Poppins, fontSize = 15.sp, fontWeight = FontWeight.Medium),
    labelLarge = TextStyle(fontFamily = Poppins, fontSize = 14.sp, fontWeight = FontWeight.Medium),
    labelMedium = TextStyle(fontFamily = Poppins, fontSize = 13.sp, fontWeight = FontWeight.Bold),
    labelSmall = TextStyle(fontFamily = Poppins, fontSize = 12.sp, fontWeight = FontWeight.Normal)
)