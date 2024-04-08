package com.example.coolbox_mobiiliprojekti_app.model

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.text.Layout
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coolbox_mobiiliprojekti_app.viewmodel.ConsumptionViewModel
import com.patrykandpatrick.vico.compose.component.fixed
import com.patrykandpatrick.vico.compose.component.rememberLayeredComponent
import com.patrykandpatrick.vico.compose.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.component.shape.dashedShape
import com.patrykandpatrick.vico.compose.component.shape.markerCorneredShape
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.core.chart.dimensions.HorizontalDimensions
import com.patrykandpatrick.vico.core.chart.insets.Insets
import com.patrykandpatrick.vico.core.component.marker.MarkerComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.shape.cornered.Corner
import com.patrykandpatrick.vico.core.component.text.TextComponent
import com.patrykandpatrick.vico.core.context.MeasureContext
import com.patrykandpatrick.vico.core.extension.copyColor
import com.patrykandpatrick.vico.core.marker.Marker
import com.patrykandpatrick.vico.core.marker.MarkerLabelFormatter

// Tämä funktio luo muistettavan markerin, joka voi olla erilainen riippuen annetusta LabelPositionista.
@Composable
internal fun rememberMarker(
    labelPosition: MarkerComponent.LabelPosition = MarkerComponent.LabelPosition.Top
): Marker {
    // Haetaan viewmodel
    val viewModel: ConsumptionViewModel = viewModel()
    // Määritellään taustamuodon muoto ja taustaväri
    val labelBackgroundShape = Shapes.markerCorneredShape(Corner.FullyRounded)
    val labelBackground =
        rememberShapeComponent(labelBackgroundShape, MaterialTheme.colorScheme.surface)
            .setShadow(
                radius = LABEL_BACKGROUND_SHADOW_RADIUS_DP,
                dy = LABEL_BACKGROUND_SHADOW_DY_DP,
                applyElevationOverlay = true,
            )

    // Muistetaan tekstimuotoilu komponenttina
    val label =
        rememberTextComponent(
            color = MaterialTheme.colorScheme.onSurface,
            background = labelBackground,
            padding = dimensionsOf(8.dp, 4.dp),
            typeface = Typeface.MONOSPACE,
            textAlignment = Layout.Alignment.ALIGN_CENTER,
            minWidth = TextComponent.MinWidth.fixed(40.dp),
        )

    // Muistetaan indikaattorikomponentit
    val indicatorFrontComponent = rememberShapeComponent(Shapes.pillShape, MaterialTheme.colorScheme.surface)
    val indicatorCenterComponent = rememberShapeComponent(Shapes.pillShape)
    val indicatorRearComponent = rememberShapeComponent(Shapes.pillShape)
    val indicator =
        rememberLayeredComponent(
            rear = indicatorRearComponent,
            front =
            rememberLayeredComponent(
                rear = indicatorCenterComponent,
                front = indicatorFrontComponent,
                padding = dimensionsOf(5.dp),
            ),
            padding = dimensionsOf(10.dp),
        )

    // Muistetaan ohjelmateksti
    val guideline =
        rememberLineComponent(
            color = MaterialTheme.colorScheme.onSurface.copy(.2f),
            thickness = 2.dp,
            shape = Shapes.dashedShape(shape = Shapes.pillShape, dashLength = 8.dp, gapLength = 4.dp),
        )

    // Define the marker label formatter
    val markerLabelFormatter: MarkerLabelFormatter = remember {
        MarkerLabelFormatter { entries, chartValues ->
            buildString {
                val consumptions = viewModel.consumptionStatsData?.values?.toList()
                val temperatures = viewModel.temperatureStatsData?.values?.toList()
                // Format the marker label using the provided entries and chartValues
                append("Column Series Data: ")
                if (consumptions != null) {
                    append(consumptions.joinToString(", "))
                }
                append("\nLine Series Data: ")
                if (temperatures != null) {
                    append(temperatures.joinToString(", "))
                }
            }
        }
    }

    // Palautetaan muistettu markeri
    return remember(label, labelPosition, indicator, guideline, markerLabelFormatter) {
        @SuppressLint("RestrictedApi")
        object : MarkerComponent(label, labelPosition, indicator, guideline) {
            init {
                // Asetetaan indikaattorin koko ja väri
                indicatorSizeDp = 36f
                onApplyEntryColor = { entryColor ->
                    indicatorRearComponent.color = entryColor.copyColor(alpha = .15f)
                    with(indicatorCenterComponent) {
                        color = entryColor
                        setShadow(radius = 12f, color = entryColor)
                    }
                }
            }

            // Määritellään, miten markerin reunat määritellään
            override fun getInsets(
                context: MeasureContext,
                outInsets: Insets,
                horizontalDimensions: HorizontalDimensions,
            ) {
                with(context) {
                    outInsets.top =
                        (
                                CLIPPING_FREE_SHADOW_RADIUS_MULTIPLIER * LABEL_BACKGROUND_SHADOW_RADIUS_DP -
                                        LABEL_BACKGROUND_SHADOW_DY_DP
                                )
                            .pixels
                    if (labelPosition == LabelPosition.AroundPoint) return
                    outInsets.top += label.getHeight(context) + labelBackgroundShape.tickSizeDp.pixels
                }
            }
        }
    }
}


const val LABEL_BACKGROUND_SHADOW_RADIUS_DP = 4f
const val LABEL_BACKGROUND_SHADOW_DY_DP = 2f
const val CLIPPING_FREE_SHADOW_RADIUS_MULTIPLIER = 1.4f
