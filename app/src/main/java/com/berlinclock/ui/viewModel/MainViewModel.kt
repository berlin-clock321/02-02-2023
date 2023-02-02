package com.berlinclock.ui.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.berlinclock.ui.BoxColor
import java.util.*
import kotlin.concurrent.fixedRateTimer

class MainViewModel : ViewModel() {

    private lateinit var fixedRateTimer: Timer
    val secondMutable: MutableState<Int> = mutableStateOf(0)

    private var firstRowArray: MutableList<BoxColor> = mutableListOf()
    val firstRowMutable: MutableState<List<BoxColor>> = mutableStateOf(firstRowArray)

    private var secondRowArray: MutableList<BoxColor> = mutableListOf()
    val secondRowMutable: MutableState<List<BoxColor>> = mutableStateOf(secondRowArray)

    private var thirdRowArray: MutableList<BoxColor> = mutableListOf()
    val thirdRowMutable: MutableState<List<BoxColor>> = mutableStateOf(thirdRowArray)

    private var fourthRowArray: MutableList<BoxColor> = mutableListOf()
    val fourthRowMutable: MutableState<List<BoxColor>> = mutableStateOf(fourthRowArray)

    private var firstRowCount = -1
    private var secondRowCount = -1
    private var thirdRowCount = -1
    private var fourthRowCount = -1

    init {
        calculateTime()
    }

    private fun calculateTime() {
        updateTime()
        fixedRateTimer = fixedRateTimer(
            name = "second timer",
            initialDelay = 1000, period = 1000, daemon = true
        ) {
            updateTime()
        }
    }

    fun updateTime() {
        val seconds = System.currentTimeMillis() / 1000
        secondMutable.value = (seconds % 2).toInt()
        val rightNow = Calendar.getInstance()
        val currentHourIn24Format: Int =
            rightNow.get(Calendar.HOUR_OF_DAY) // return the hour in 24 hrs format (ranging from 0-23)
        val minutes: Int = rightNow.get(Calendar.MINUTE)
        firstRowMutable.value = getFirstRowHours(currentHourIn24Format)
        secondRowMutable.value = getSecondRowHours(currentHourIn24Format)
        thirdRowMutable.value = getThirdMinutesRow(minutes)
        fourthRowMutable.value = getFourthMinutesRow(minutes)
    }


    fun getFirstRowHours(hours: Int): List<BoxColor> {
        val firstRow = hours / 5
        if (firstRowCount != firstRow) {
            firstRowArray = mutableListOf()
            for (index in 0..3) {
                firstRowArray.add(if (index < firstRow) BoxColor.RED else BoxColor.WHITE)
            }
            firstRowCount = firstRow
        }


        return firstRowArray
    }

    fun getSecondRowHours(hours: Int): List<BoxColor> {
        val secondRow = hours % 5
        if (secondRow != secondRowCount) {
            secondRowArray = mutableListOf()
            for (index in 0..3) {
                secondRowArray.add(if (index < secondRow)  BoxColor.RED else BoxColor.WHITE)
            }
            secondRowCount = secondRow
        }

        return secondRowArray
    }

    fun getThirdMinutesRow(minutes: Int): List<BoxColor> {
        val thirdRow = minutes / 5
        if (thirdRow != thirdRowCount) {
            thirdRowArray = mutableListOf()
            for (index in 0..10) {
                thirdRowArray.add(if (index < thirdRow) (if (index % 3 == 2)  BoxColor.RED else BoxColor.YELLOW) else BoxColor.WHITE)
            }
            thirdRowCount = thirdRow
        }

        return thirdRowArray
    }

    fun getFourthMinutesRow(minutes: Int): List<BoxColor> {
        val fourthRow = minutes % 5
        if (fourthRow != fourthRowCount) {
            fourthRowArray = mutableListOf()
            for (index in 0..3) {
                fourthRowArray.add(if (index < fourthRow) BoxColor.YELLOW else BoxColor.WHITE)
            }
            fourthRowCount = fourthRow
        }

        return fourthRowArray
    }

    override fun onCleared() {
        fixedRateTimer.cancel();
        super.onCleared()
    }
}