package com.example.ychartsnecoexample

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.PlotType
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.example.ychartsnecoexample.ui.theme.YChartsNecoExampleTheme
import kotlin.random.Random


const val STEPS = 10

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val pointsList = getPointsList()
            val xAxisData = AxisData.Builder()
                .axisStepSize(100.dp)
                .backgroundColor(Color.Transparent)
                .steps(pointsList.size-1)
                .labelData { i -> "$i day" }
                .labelAndAxisLinePadding(15.dp)
                .build()
            val yAxisData = AxisData.Builder()
                .steps(STEPS)
                .backgroundColor(Color.Transparent)
                .labelData { i ->
                    val yScale = (getMax(pointsList)-getMin(pointsList)) / STEPS.toFloat()
                    val n = ((i*yScale) + getMin(pointsList))
                    String.format("%.1f", n)
                }
                .labelAndAxisLinePadding(20.dp)
                .build()

            YChartsNecoExampleTheme {
                val lineChartData = LineChartData(
                    linePlotData = LinePlotData(
                        lines = listOf(
                            Line(
                                dataPoints = pointsList,
                                LineStyle(
                                    color = Color.Green,
                                    width = 2f
                                ),
                                IntersectionPoint(
                                    color = Color.Blue,
                                    radius = 5.dp
                                ),
                                SelectionHighlightPoint(),
                                ShadowUnderLine(),
                                SelectionHighlightPopUp()
                            )
                        )
                    ),
                    xAxisData = xAxisData,
                    yAxisData = yAxisData,
                    gridLines = GridLines(),
                    backgroundColor = Color.White
                )

                Scaffold(
                    topBar = {

                    }
                ) {
                    LineChart(
                        modifier = Modifier
                            .height(300.dp)
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        lineChartData = lineChartData
                    )
                }
                

            }
        }
    }

    private fun getPointsList(): List<Point> {
        val list = ArrayList<Point>()
        for(i in 0..31) {
            list.add(
                Point(
                    i.toFloat(),
                    Random.nextInt(50, 90).toFloat()
                )
            )
        }
        return list
    }

    private fun getMax(list: List<Point>): Float {
        var max = 0f
        list.forEach {
            if(max<it.y) max = it.y
        }
        return max
    }

    private fun getMin(list: List<Point>): Float {
        var min = Float.MAX_VALUE;
        list.forEach {
            if(min>it.y) min = it.y
        }
        return min
    }
}

