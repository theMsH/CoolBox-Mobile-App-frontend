package com.example.coolbox_mobiiliprojekti_app.ui.theme

import androidx.compose.ui.graphics.Color


// Light Theme
val lightColors: List<Color> = listOf(
    Color(0xFF363C55),  // TextsDarkColor
    Color(0xFFCAD6DF),  // TextsLightColor

    Color(0xff001849),  // PanelColor
    Color(0xFFF0F0F0),  // ScreenPanelColor
    Color(0xff495d92),  // PanelTextColor
    Color.White,              // PanelTextButtonColor

    Color(0xFFEEEFFF),  // DrawerLayoutColor
    Color(0xff001849),  // CoolAppText
    Color(0xFFE6EAFF),  // TopAppBarColor
    Color(0xFFCFD7FF),  // BottomAppBarColor
    Color(0xFFE6EAFF),  // BottomAppBarColorSecondary

    Color(0xFF03396C),  // PrimaryColor
    Color(0xFFCFDDFF),  // PrimaryContentsColor
    Color(0xFFB2BFE2),  // SecondaryColor
    Color(0xFFFFFFFF),  // SecondaryContentsColor
    Color(0xFF1F5FFF),  // TertiaryColor
    Color(0xFF3F4758),  // OutlineColor
    Color(0xFFE4ECF1),  // BackgroundColor

    Color(0xFF3B7DFF),  // GraphKwhColorPanel
    Color(0xFF0D3E9E),  // GraphKwhColor
    Color(0xFFE04646),  // GraphTempColor
    Color.Green,              // ProductionLineColor

    Color.Green,              // GoodBatteryChargeColor
    Color.Yellow,             // SatisfyingBatteryChargeColor
    Color(0xFFFFA500),  // TolerableBatteryChargeColor
    Color.Red                 // BadBatteryChargeColor
)

// Dark Theme
val darkColors: List<Color> = listOf(
    Color(0xFF363C55),  // TextsDarkColor
    Color(0xFF8B9399),  // TextsLightColor

    Color(0xFF0F0F0F),  // PanelColor
    Color(0xFF0F0F0F),  // ScreenPanelColor
    Color(0xFF9FA5B8),  // PanelTextColor
    Color(0xFFC4CADB),  // PanelTextButtonColor

    Color(0xFF222222),  // DrawerLayoutColor
    Color(0xFF859BC7),  // CoolAppText
    Color(0xFF090D24),  // TopAppBarColor
    Color(0xFF11101A),  // BottomAppBarColor
    Color(0xFF1E1F24),  // BottomAppBarColorSecondary

    Color(0xFF6C72B3),  // PrimaryColor
    Color(0xFF171718),  // PrimaryContentsColor
    Color(0xFF586381),  // SecondaryColor
    Color(0xFFFFFFFF),  // SecondaryContentsColor
    Color(0xFF6C81B3),  // TertiaryColor
    Color(0xFFCCCCCC),  // OutlineColor
    Color(0xFFE4ECF1),  // BackgroundColor

    Color(0xFF7595D5),  // GraphKwhColorPanel
    Color(0xFF7595D5),  // GraphKwhColor
    Color(0xFFFF6969),  // GraphTempColor
    Color(0xFF78DA7A),  // ProductionLineColor

    Color(0xFF6DD640),  // GoodBatteryChargeColor
    Color(0xFFD6D440),  // SatisfyingBatteryChargeColor
    Color(0xFFD67040),  // TolerableBatteryChargeColor
    Color(0xFFEB3838)   // BadBatteryChargeColor
)


// Vaihda tästä teeman väri jos haluat nähdä miltä tummateema voisi näyttää atm
val colorList = lightColors

val TextsDarkColor = colorList[0]
val TextsLightColor = colorList[1]

val PanelColor = colorList[2]
val ScreenPanelColor = colorList[3]
val PanelTextColor = colorList[4]
val PanelTextButtonColor = colorList[5]

val DrawerLayoutColor = colorList[6]
val CoolAppText = colorList[7]
val TopAppBarColor = colorList[8]
val BottomAppBarColor = colorList[9]
val BottomAppBarColorSecondary = colorList[10]

val PrimaryColor = colorList[11]
val PrimaryContentsColor = colorList[12]
val SecondaryColor = colorList[13]
val SecondaryContentsColor = colorList[14]
val TertiaryColor = colorList[15]
val OutlineColor = colorList[16]
val BackgroundColor = colorList[17]

val GraphKwhColorPanel = colorList[18]
val GraphKwhColor = colorList[19]
val GraphTempColor = colorList[20]
val ProductionLineColor = colorList[21]

val GoodBatteryChargeColor = colorList[22]
val SatisfyingBatteryChargeColor = colorList[23]
val TolerableBatteryChargeColor = colorList[24]
val BadBatteryChargeColor = colorList[25]