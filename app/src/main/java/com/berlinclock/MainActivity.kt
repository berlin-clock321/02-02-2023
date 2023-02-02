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
import com.berlinclock.ui.constants.AppConstant.BIG_BOX_COUNT
import com.berlinclock.ui.constants.AppConstant.BIG_BOX_SPACE
import com.berlinclock.ui.constants.AppConstant.BOX_HEIGHT
import com.berlinclock.ui.constants.AppConstant.BOX_PADDING
import com.berlinclock.ui.constants.AppConstant.CIRCLE_BOX
import com.berlinclock.ui.constants.AppConstant.CIRCLE_DIA
import com.berlinclock.ui.constants.AppConstant.DIGITAL_CLOCK_FORMAT
import com.berlinclock.ui.constants.AppConstant.OUTER_RECTANGLE_WIDTH
import com.berlinclock.ui.constants.AppConstant.SCREEN_PADDING
import com.berlinclock.ui.constants.AppConstant.SMALL_BOX_COUNT
import com.berlinclock.ui.constants.AppConstant.SMALL_BOX_SPACE
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
            .padding(SCREEN_PADDING.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        //Seconds Blinking Outer Black Circle
        Box(contentAlignment = Alignment.Center) {
            Canvas(modifier = Modifier
                .size(CIRCLE_BOX.dp), onDraw = {
                drawCircle(color = Color.Black)
            })
            //Seconds Blinking Inner Circle
            Canvas(modifier = Modifier
                .size(CIRCLE_DIA.dp), onDraw = {
                drawCircle(
                    color = if (viewModel.secondMutable.value == 0) Color.Yellow else Color.White
                )
            })
        }


        Spacer(modifier = Modifier.height(20.dp))
        val whiteSpaceForBiggerBoxRow = BIG_BOX_SPACE.dp
        val whiteSpaceForSmallBoxRow = SMALL_BOX_SPACE.dp
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp
        val widthBigBox =
            ((screenWidth.value - whiteSpaceForBiggerBoxRow.value) / BIG_BOX_COUNT).toInt();
        val widthSmallBox =
            ((screenWidth.value - whiteSpaceForSmallBoxRow.value) / SMALL_BOX_COUNT).toInt()

        //Hours 1st Row
        DrawRow(charsList = viewModel.firstRowMutable.value, widthBigBox)
        Spacer(modifier = Modifier.height(SCREEN_PADDING.dp))

        //Hours 2nd Row
        DrawRow(charsList = viewModel.secondRowMutable.value, widthBigBox)
        Spacer(modifier = Modifier.height(SCREEN_PADDING.dp))

        //minutes 1st Row
        DrawRow(charsList = viewModel.thirdRowMutable.value, widthSmallBox)
        Spacer(modifier = Modifier.height(SCREEN_PADDING.dp))
        //minutes 2nd Row
        DrawRow(charsList = viewModel.fourthRowMutable.value, widthBigBox)

        //Digital Clock
        AndroidView(
            factory = { context ->
                TextClock(context).apply {
                    format12Hour?.let { this.format12Hour = DIGITAL_CLOCK_FORMAT }
                    timeZone?.let { this.timeZone = it }
                    textSize.let { this.textSize = 50f }
                }
            },
            modifier = Modifier.padding(SCREEN_PADDING.dp),
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
                .size(size.dp, BOX_HEIGHT.dp)
                .background(color = Color.Black)
                .padding(OUTER_RECTANGLE_WIDTH.dp),
            onDraw = {
                drawRect(
                    color = when (char) {
                        BoxColor.RED -> Color.Red
                        BoxColor.YELLOW -> Color.Yellow
                        else -> Color.White
                    }
                )
            })
        Spacer(modifier = Modifier.padding(BOX_PADDING.dp))
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



