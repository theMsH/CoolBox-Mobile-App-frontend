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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.coolbox_mobiiliprojekti_app.ui.theme.BadBatteryChargeColor
import com.example.coolbox_mobiiliprojekti_app.ui.theme.BadBatteryChargeColorDark
import com.example.coolbox_mobiiliprojekti_app.ui.theme.GoodBatteryChargeColor
import com.example.coolbox_mobiiliprojekti_app.ui.theme.GoodBatteryChargeColorDark
import com.example.coolbox_mobiiliprojekti_app.ui.theme.SatisfyingBatteryChargeColor
import com.example.coolbox_mobiiliprojekti_app.ui.theme.SatisfyingBatteryChargeColorDark
import com.example.coolbox_mobiiliprojekti_app.ui.theme.TolerableBatteryChargeColor
import com.example.coolbox_mobiiliprojekti_app.ui.theme.TolerableBatteryChargeColorDark
import com.example.coolbox_mobiiliprojekti_app.viewmodel.BatteryViewModel

@Composable
fun BatteryChart() {
    val viewModel: BatteryViewModel = viewModel()
    val fullCharge = 100.0f
    val stateOfCharge: Float = viewModel.batteryChartState.value.soc
    val missingCharge: Float = fullCharge - stateOfCharge
    var customColor: Color = if (isSystemInDarkTheme()) GoodBatteryChargeColorDark else GoodBatteryChargeColor

    when {
        stateOfCharge < 25 -> {
            customColor = if (isSystemInDarkTheme()) BadBatteryChargeColorDark else BadBatteryChargeColor
        }

        stateOfCharge < 50 -> {
            customColor = if (isSystemInDarkTheme()) TolerableBatteryChargeColorDark else TolerableBatteryChargeColor
        }

        stateOfCharge < 75 -> {
            customColor = if (isSystemInDarkTheme()) SatisfyingBatteryChargeColorDark else SatisfyingBatteryChargeColor
        }
    }

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
        strokeWidth = 20f,
        isAnimationEnable = true,
        animationDuration = 600
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        when {
            viewModel.batteryChartState.value.loading -> CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )

            else -> Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 20.dp),
                    fontSize = 20.sp,
                    text = stringResource(R.string.battery),
                    textAlign = TextAlign.Center
                )
                Box() {
                    DonutPieChart(
                        modifier = Modifier
                            .width(100.dp)
                            .height(100.dp)
                            .background(color = MaterialTheme.colorScheme.secondary),
                        pieChartData = donutChartData,
                        pieChartConfig = donutChartConfig
                    )
                    Text(
                        text = "$stateOfCharge %",
                        color = Color(0xFFC6CDEC),
                        fontSize = 16.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                } // Graafin Box loppuu
            }
        }
    } // Paneeli Box loppuu
}