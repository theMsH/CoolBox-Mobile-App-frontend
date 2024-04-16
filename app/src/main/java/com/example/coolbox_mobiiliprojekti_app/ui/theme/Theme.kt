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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColorDark,                             // Ylin palkki, Päätekstin väri, Nappien tausta, loadingCircle
    onPrimary = PrimaryContentsColorDark,                   // Nappi tekstit
    //primaryContainer = Color.Red,
    onPrimaryContainer = CoolAppTextDark,                   // Ei vaikuta defaultisti mihinkään tässä softassa.
    //inversePrimary = Color.Red,
    secondary = PanelColorDark,                             // Ei vaikuta defaultisti mihinkään tässä softassa.
    onSecondary = PanelTextColorDark,                       // Card textit
    secondaryContainer = SecondaryColorDark,                // Selected menuitem
    onSecondaryContainer = SecondaryContentsColorDark,      // Menuitemien sisällön väri
    tertiary =  TertiaryColorDark,
    onTertiary = TextsColorDark,                            // No account? -text
    //tertiaryContainer = Color.Red,
    //onTertiaryContainer = Color.Red,
    background = BackgroundColorDark,                       // Tausta
    surface = DrawerLayoutColorDark,                        // TopAppBar, drawerlayout, BottomAppBar
    onSurface = TextsColorDark,                             // Titlet / Disabled nappi tausta, Textfieldin text
    surfaceVariant = PanelColorDark,                        // Paneelit (cards), disabled Toggle slider tausta.
    onSurfaceVariant = TextsColorDark,                      // Titlet Cardseissa, menuitemit, hamburgericon, textfield placeholder ja silmä
    //surfaceTint = Color.Red,
    //inverseSurface = Color.Red,
    inverseOnSurface = PanelTextButtonColorDark,            // Ei vaikuta defaultisti mihinkään tässä softassa.
    //error = Color.Red,
    //onError = Color.Red,
    //onErrorContainer = Color.Red,
    outline = OutlineColorDark,                             // Textfieldien ja togglebuttoneiden outline
    outlineVariant = OutlineColorDark,                      // Dividerviiva drawerlayoutissa
    //surfaceBright = Color.Red,
    surfaceContainer = TopAppBarColorDark,                  // Ei vaikuta defaultisti mihinkään tässä softassa.
    surfaceContainerHigh = ScreenPanelColorDark,            // Ei vaikuta defaultisti mihinkään tässä softassa.
    surfaceContainerHighest = PanelTextColorBrightDark,     // Ei vaikuta defaultisti mihinkään tässä softassa.
    surfaceContainerLow = BottomAppBarColorSecondaryDark,   // Ei vaikuta defaultisti mihinkään tässä softassa.
    surfaceContainerLowest = BottomAppBarColorDark,         // Ei vaikuta defaultisti mihinkään tässä softassa.
    //surfaceDim = Color.Red
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,                             // Ylin palkki, Päätekstin väri, Nappien tausta, loadingCircle
    onPrimary = PrimaryContentsColor,                   // Nappi tekstit
    //primaryContainer = Color.Red,
    onPrimaryContainer = CoolAppText,                   // Ei vaikuta defaultisti mihinkään tässä softassa.
    //inversePrimary = Color.Red,
    secondary = PanelColor,                             // Ei vaikuta defaultisti mihinkään tässä softassa.
    onSecondary = PanelTextColor,                       // Card textit
    secondaryContainer = SecondaryColor,                // Selected menuitem
    onSecondaryContainer = SecondaryContentsColor,      // Menuitemien sisällön väri
    tertiary =  TertiaryColor,
    onTertiary = TextsColor,                            // No account? -text
    //tertiaryContainer = Color.Red,
    //onTertiaryContainer = Color.Red,
    background = BackgroundColor,                       // Tausta
    surface = DrawerLayoutColor,                        // TopAppBar, drawerlayout, BottomAppBar
    onSurface = TextsColor,                             // Titlet / Disabled nappi tausta, Textfieldin text
    surfaceVariant = PanelColor,                        // Paneelit (cards), disabled Toggle slider tausta.
    onSurfaceVariant = TextsColor,                      // Titlet Cardseissa, menuitemit, hamburgericon, textfield placeholder ja silmä
    //surfaceTint = Color.Red,
    //inverseSurface = Color.Red,
    inverseOnSurface = PanelTextButtonColor,            // Ei vaikuta defaultisti mihinkään tässä softassa.
    //error = Color.Red,
    //onError = Color.Red,
    //onErrorContainer = Color.Red,
    outline = OutlineColor,                             // Textfieldien ja togglebuttoneiden outline
    outlineVariant = OutlineColor,                      // Dividerviiva drawerlayoutissa
    //surfaceBright = Color.Red,
    surfaceContainer = TopAppBarColor,                  // Ei vaikuta defaultisti mihinkään tässä softassa.
    surfaceContainerHigh = ScreenPanelColor,            // Ei vaikuta defaultisti mihinkään tässä softassa.
    surfaceContainerHighest = PanelTextColorBright,     // Ei vaikuta defaultisti mihinkään tässä softassa.
    surfaceContainerLow = BottomAppBarColorSecondary,   // Ei vaikuta defaultisti mihinkään tässä softassa.
    surfaceContainerLowest = BottomAppBarColor,         // Ei vaikuta defaultisti mihinkään tässä softassa.
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