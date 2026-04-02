package com.example.praktam2_2417051054.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val AppColorScheme = lightColorScheme(
    primary = AbuTua,
    onPrimary = SurfacePutih,
    background = Background,
    surface = SurfacePutih,
    onSurface = HitamTeks,
    secondary = AbuBiasa,
    surfaceVariant = PutihBuram
)

@Composable
fun PrakTAM2_2417051054Theme(content: @Composable () -> Unit) {
        MaterialTheme(
            colorScheme = AppColorScheme,
            typography = Typography,
            content = content
        )
}