package com.example.coolbox_mobiiliprojekti_app.view

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ConsumptionColumnChart(
    chartData: Map<String, Float?>?,
    maxValue: Float = 10f
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
            .padding(20.dp, 40.dp, 40.dp, 40.dp)
    ) {
        drawAxes(maxValue)
        drawData(chartData, maxValue)
    }
}

fun DrawScope.drawAxes(
    maxValue: Float
) {
    // Piirrä pystyakseli (arvot)
    drawLine(
        color = Color.Black,
        start = Offset(50f, size.height),
        end = Offset(50f, 0f)
    )
    // Piirrä vaaka-akseli (päivämäärät)
    drawLine(
        color = Color.Black,
        start = Offset(50f, size.height),
        end = Offset(size.width, size.height)
    )
    // Piirrä arvomerkinnät
    for (i in 0..10) {
        val y = size.height - (i.toFloat() / 10f) * size.height
        drawLine(
            color = Color.Black,
            start = Offset(45f, y),
            end = Offset(50f, y)
        )
        drawIntoCanvas { canvas ->
            val paint = Paint().apply {
                color = Color.Black.toArgb()
                textSize = 12.sp.toPx()
                textAlign = Paint.Align.RIGHT
            }
            val text = (maxValue * (i.toFloat() / 10f)).toString()
            canvas.nativeCanvas.drawText(
                text,
                40f, // Säädä tekstin X-asema
                size.height - (i.toFloat() / 10f) * size.height + 6.dp.toPx(), // Säädä tekstin Y-asema
                paint
            )
        }
    }
}

fun DrawScope.drawData(
    chartData: Map<String, Float?>?,
    maxValue: Float
) {
    chartData?.let { data ->
        val columnCount = data.size
        val columnWidth = (size.width - 50f) / columnCount
        val columnSpacing = 1.dp.toPx()
        var currentX = 50f + columnSpacing
        var drawDate = true // Lippu ilmaisemaan, pitäisikö päivämäärä piirtää

        for ((index, entry) in data.entries.withIndex()) {
            val (day, value) = entry
            val columnHeight = (value?.div(maxValue))?.times(size.height)
            val startY = size.height - columnHeight!!
            drawRect(
                color = Color.Blue,
                topLeft = Offset(currentX, startY),
                size = Size(columnWidth - columnSpacing * 2, columnHeight)
            )
            drawIntoCanvas { canvas ->
                val paint = Paint().apply {
                    color = Color.Black.toArgb()
                    textSize =
                        if (columnCount > 7) {
                            14.sp.toPx()
                        } else {
                            12.sp.toPx()
                        }
                    textAlign = Paint.Align.CENTER
                }
                val textWidth = paint.measureText(day.toString())
                val textX = currentX + (columnWidth - columnSpacing * 2) / 2
                val textY =
                    size.height + 20.dp.toPx() // Säädä pystysuuntainen asento palkkien alapuolelle

                if (drawDate) {
                    // Katkaise päivämäärämerkkijono, jos se on liian pitkä mahtuakseen sarakkeen leveyteen
                    val truncatedDay = if (day.length == 10) {
                        val monthOnly = day.substring(5, 7) // Poimi vain kuukausi
                        val dayOnly = day.substring(8, 10) // Poimi vain päivä
                        "$dayOnly/$monthOnly"
                    } else {
                        day
                    }
                    canvas.nativeCanvas.drawText(
                        truncatedDay,
                        textX,
                        textY,
                        paint
                    )
                }
            }

            if (columnCount > 7) {
                // Käännä drawDate-lippu seuraavaa iteraatiota varten, jos columnCount on suurempi kuin 7
                drawDate = !drawDate
            }

            currentX += columnWidth + columnSpacing
        }
    }
}
