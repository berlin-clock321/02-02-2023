package com.berlinclock

import android.os.Bundle
import android.widget.TextClock
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.berlinclock.ui.BoxColor
import com.berlinclock.ui.theme.BerlinClockTheme
import com.berlinclock.ui.viewModel.MainViewModel


class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BerlinClockTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainLayout(viewModel)
                }
            }
        }
    }
}

@Composable
fun MainLayout(viewModel: MainViewModel) {
    Column(
        Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(contentAlignment = Alignment.Center) {
            Canvas(modifier = Modifier
                .size(100.dp), onDraw = {
                drawCircle(color = Color.Black)
            })
            Canvas(modifier = Modifier
                .size(98.dp), onDraw = {
                drawCircle(color = if (viewModel.secondMutable.value == 0) Color.Yellow else Color.White)
            })
        }


        Spacer(modifier = Modifier.height(20.dp))
        val whiteSpaceForBiggerBoxRow = (20 + 3 * 4).dp
        val whiteSpaceForSmallBoxRow = (20 + 3 * 11).dp
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp
        val width4 = ((screenWidth.value - whiteSpaceForBiggerBoxRow.value) / 4).toInt();
        val width11 = ((screenWidth.value - whiteSpaceForSmallBoxRow.value) / 11).toInt()

        DrawRow(charsList = viewModel.firstRowMutable.value, width4)
        Spacer(modifier = Modifier.height(5.dp))
        DrawRow(charsList = viewModel.secondRowMutable.value, width4)
        Spacer(modifier = Modifier.height(5.dp))

        //minutes Rows
        DrawRow(charsList = viewModel.thirdRowMutable.value, width11)
        Spacer(modifier = Modifier.height(5.dp))
        DrawRow(charsList = viewModel.fourthRowMutable.value, width4)

        AndroidView(
            factory = { context ->
                TextClock(context).apply {
                    format12Hour?.let { this.format12Hour = "kk:mm" }
                    timeZone?.let { this.timeZone = it }
                    textSize.let { this.textSize = 50f }
                }
            },
            modifier = Modifier.padding(5.dp),
        )
    }


}

@Composable
fun DrawRow(charsList: List<BoxColor>, size: Int) {
    LazyRow() {
        items(charsList) {
            EachRow(it, size)
        }
    }
}

@Composable
fun EachRow(char: BoxColor, size: Int) {
    Row(
        Modifier
            .background(color = Color.White)
            .fillMaxWidth(),
    ) {
        Canvas(
            modifier = Modifier
                .size(size.dp, 60.dp)
                .background(color = Color.Black)
                .padding(2.dp),
            onDraw = {
                drawRect(color = if (char == BoxColor.RED) Color.Red else if (char == BoxColor.YELLOW) Color.Yellow else Color.White)
            })
        Spacer(modifier = Modifier.padding(2.dp))
    }


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BerlinClockTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            MainLayout(MainViewModel())
        }
    }
}



