package com.example.eugenpro.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val OrangePrimary = Color(0xFFFF8C42)      // Акцент: оранжевый
private val DarkBlueBg = Color(0xFF1A2F3C)         // Фон тёмной темы
private val GraphiteSurface = Color(0xFF4A5568)    // Поверхности тёмной темы
private val GraySecondary = Color(0xFF2D3748)      // Вторичные элементы
private val LightBg = Color(0xFFFFFFFF)            // Фон светлой темы
private val LightSurface = Color(0xFFF8F9FA)       // Поверхности светлой темы
private val LightOnSurface = Color(0xFF2D3748)     // Текст на светлом
private val OutlineDark = Color(0xFF64748B)        // Границы тёмные
private val OutlineLight = Color(0xFFCBD5E1)       // Границы светлые
private val ErrorColor = Color(0xFFEF4444)         // Ошибки
private val DarkColorScheme = darkColorScheme(
    primary = OrangePrimary,
    onPrimary = Color.White,
    secondary = GraySecondary,
    onSecondary = Color.White,
    tertiary = OrangePrimary.copy(alpha = 0.8f),
    onTertiary = Color.White,
    background = DarkBlueBg,
    onBackground = Color.White,
    surface = GraphiteSurface,
    onSurface = Color.White,
    surfaceVariant = GraySecondary,
    onSurfaceVariant = Color.White.copy(alpha = 0.85f),
    outline = OutlineDark,
    outlineVariant = OutlineDark.copy(alpha = 0.6f),
    error = ErrorColor,
    onError = Color.White,
    inversePrimary = OrangePrimary.copy(alpha = 0.9f),
    inverseSurface = Color.White,
    inverseOnSurface = DarkBlueBg,
)

private val LightColorScheme = lightColorScheme(
    primary = OrangePrimary,
    onPrimary = Color.White,
    secondary = GraySecondary,
    onSecondary = Color.White,
    tertiary = OrangePrimary.copy(alpha = 0.8f),
    onTertiary = Color.White,
    background = LightBg,
    onBackground = DarkBlueBg,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = Color(0xFFE2E8F0),
    onSurfaceVariant = GraySecondary,
    outline = OutlineLight,
    outlineVariant = OutlineLight.copy(alpha = 0.6f),
    error = ErrorColor,
    onError = Color.White,
    inversePrimary = OrangePrimary.copy(alpha = 0.9f),
    inverseSurface = DarkBlueBg,
    inverseOnSurface = Color.White,
)

@Composable
fun EugenProTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}