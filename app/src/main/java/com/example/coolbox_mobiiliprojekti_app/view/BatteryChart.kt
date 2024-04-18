package com.example.coolbox_mobiiliprojekti_app.view

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.coolbox_mobiiliprojekti_app.R
import com.example.coolbox_mobiiliprojekti_app.datastore.UserPreferences
import com.example.coolbox_mobiiliprojekti_app.viewmodel.BatteryViewModel
import kotlin.math.round

@Composable
fun BatteryChart() {
    val viewModel: BatteryViewModel = viewModel()
    val fullCharge = 100.0f
    val stateOfCharge: Float = viewModel.batteryChartState.value.soc
    val missingCharge: Float = fullCharge - stateOfCharge

    val context = LocalContext.current
    val darkTheme = UserPreferences(context).getDarkMode.collectAsState(isSystemInDarkTheme()).value

    // Määritellään graafin väri akun varauksen mukaan:
    var red = 255
    var green = 0

    if (stateOfCharge > 60) {
        red = round(255 - (stateOfCharge - 60) * 10).toInt()
        if (red < 0) red = 0
        green = 255
        if (darkTheme) green = 150
    }
    else if (stateOfCharge > 20) {
        red = 255
        if (darkTheme) red = 150
        green = round((stateOfCharge - 20) * 7).toInt()
        if (darkTheme && green > 255) {
            green = 150
        }
        if (green > 255) green = 255
    }
    else {
        if (darkTheme) red = 150
    }

    val customColor = Color(red, green, 0)

    val donutChartData = PieChartData(
        slices = listOf(
            PieChartData.Slice(
                label = stringResource(R.string.state_of_charge),
                value = stateOfCharge,
                color = customColor
            ),
            PieChartData.Slice(
                label = stringResource(R.string.missing_charge),
                value = missingCharge,
                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = .3f)
            )
        ),
        plotType = PlotType.Donut
    )

    val donutChartConfig = PieChartConfig(
        isClickOnSliceEnabled = false,
        strokeWidth = 35f,
        isAnimationEnable = true,
        animationDuration = 600
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        when {
            viewModel.batteryChartState.value.loading -> Box(
                modifier = Modifier
                    .height(184.dp)
                    .align(Alignment.Center)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 20.dp),
                    fontSize = 20.sp,
                    text = stringResource(R.string.battery_charge),
                    textAlign = TextAlign.Center
                )
                Box() {
                    DonutPieChart(
                        modifier = Modifier
                            .width(140.dp)
                            .height(140.dp)
                            .background(color = MaterialTheme.colorScheme.secondary),
                        pieChartData = donutChartData,
                        pieChartConfig = donutChartConfig
                    )
                    Text(
                        text = "$stateOfCharge %",
                        color = Color(0xFFC6CDEC),
                        fontSize = 25.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                } // Graafin Box loppuu
            }
        }
    } // Paneeli Box loppuu
}