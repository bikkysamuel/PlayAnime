package io.github.bikkysamuel.playanime.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import io.github.bikkysamuel.playanime.ui.viewmodels.MainViewModel

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

private val OrangeDarkColorScheme = darkColorScheme(
    primary = Orange80,
    secondary = OrangeGrey80,
    tertiary = Yellow80
)

private val OrangeLightColorScheme = lightColorScheme(
    primary = Orange40,
    secondary = OrangeGrey40,
    tertiary = Yellow40
)

@Composable
fun PlayAnimeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    appTheme: MainViewModel.ThemeSet = MainViewModel.ThemeSet.SYSTEM_DEFAULT,
    appFontStyle: MainViewModel.FontStyleSet = MainViewModel.FontStyleSet.SYSTEM_DEFAULT,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        appTheme == MainViewModel.ThemeSet.SYSTEM_DEFAULT
                && dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        appTheme == MainViewModel.ThemeSet.DARK -> DarkColorScheme
        appTheme == MainViewModel.ThemeSet.LIGHT -> LightColorScheme
        appTheme == MainViewModel.ThemeSet.ORANGE_DARK -> OrangeDarkColorScheme
        appTheme == MainViewModel.ThemeSet.ORANGE_LIGHT -> OrangeLightColorScheme
        else -> if (darkTheme) DarkColorScheme else LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = if (appFontStyle == MainViewModel.FontStyleSet.NOTABLE) TypographyNotable else Typography,
        content = content
    )
}