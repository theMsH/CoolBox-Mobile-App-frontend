package com.example.coolbox_mobiiliprojekti_app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,                      // Ylin palkki, Päätekstin väri, Nappien tausta, loadingCircle
    onPrimary = PrimaryContentsColor,            // Nappi tekstit
    //primaryContainer = Color.Red,
    //onPrimaryContainer = Color.Red,
    //inversePrimary = Color.Red,
    //secondary = SecondaryColor,
    //onSecondary = SecondaryContentsColor,
    secondaryContainer = SecondaryColor,           // Selected menuitem
    //onSecondaryContainer = SecondaryContentsColor, // Menuitemien sisällön väri
    //tertiary =  TertiaryColor,
    //onTertiary = TextsDarkColor,                       // No account? -text
    //tertiaryContainer = Color.Red,
    //onTertiaryContainer = Color.Red,
    //background = BackgroundColor,                  // Tausta
    surface = DrawerLayoutColor,                 // TopAppBar, drawerlayout, BottomAppBar
    //onSurface = TextsDarkColor,                      // Titlet / Disabled nappi tausta, Textfieldin text
    //surfaceVariant = SecondaryColor,           // Paneelit (cards), disabled Toggle slider tausta.
    //onSurfaceVariant = TextsDarkColor,               // Titlet Cardseissa, menuitemit, hamburgericon, textfield placeholder ja silmä
    //surfaceTint = Color.Red,
    //inverseSurface = Color.Red,
    //inverseOnSurface = Color.Red,
    //error = Color.Red,
    //onError = Color.Red,
    //onErrorContainer = Color.Red,
    //outline = OutlineColor,                      // Textfieldien ja togglebuttoneiden outline
    //outlineVariant = OutlineColor,               // Dividerviiva drawerlayoutissa
    //scrim = Color.Black,                       // Drawerlayout taustan tummennus
    //surfaceBright = Color.Red,
    //surfaceContainer = Color.Red,
    //surfaceContainerHigh = Color.Red,
    //surfaceContainerHighest = Color.Red,
    //surfaceContainerLow = Color.Red,
    //surfaceContainerLowest = Color.Red,
    //surfaceDim = Color.Red
    )

@Composable
fun CoolBoxmobiiliprojektiAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
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
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}