package com.berlinclock.ui.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.berlinclock.ui.BoxColor
import com.berlinclock.ui.constants.AppConstant.TIMER_SECOND_DELAY
import java.util.*
import kotlin.concurrent.fixedRateTimer

class MainViewModel : ViewModel() {

    private val second = 1000
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
    private var seconds: Long = 0L

    init {
        calculateTime()
    }


    private fun calculateTime() {
        seconds = System.currentTimeMillis() / second
        updateTime()

        //Set a repeated timer with 1 second interval
        fixedRateTimer = fixedRateTimer(
            name = "second timer",
            initialDelay = TIMER_SECOND_DELAY, period = TIMER_SECOND_DELAY, daemon = true
        ) {
            seconds++
            updateTime()
        }
    }

    /**
     *This function is used to calculate current time
     */
    private fun updateTime() {
        secondMutable.value = (seconds % 2).toInt()
        val rightNow = Calendar.getInstance()
        val currentHourIn24Format: Int =
            rightNow.get(Calendar.HOUR_OF_DAY) // returns the hour in 24 hrs format (ranging from 0-23)
        val minutes: Int = rightNow.get(Calendar.MINUTE)
        firstRowMutable.value = getFirstRowHours(currentHourIn24Format)
        secondRowMutable.value = getSecondRowHours(currentHourIn24Format)
        thirdRowMutable.value = getThirdMinutesRow(minutes)
        fourthRowMutable.value = getFourthMinutesRow(minutes)
    }

    /**
     * Return first hours row list
     *This function is used to determine First row hours boxes colors
     * each box indicates 5 hrs
     */
    fun getFirstRowHours(hours: Int): List<BoxColor> {
        val firstRow = hours / 5 // number of firstRow indicates RED color boxes each 5 hrs

        //if firstRowCount and firstRow is same then this row needs no modification
        if (firstRowCount != firstRow) {
            firstRowArray = mutableListOf()
            for (index in 0..3) {
                firstRowArray.add(if (index < firstRow) BoxColor.RED else BoxColor.WHITE)
            }
            firstRowCount = firstRow
        }


        return firstRowArray
    }

    /**
     * Return seconds hours row list
     *This function is used to determine second  hours row boxes colors
     * each box indicate 1 hr
     */
    fun getSecondRowHours(hours: Int): List<BoxColor> {
        val secondRow = hours % 5  // number of secondRow indicates RED color boxes each 1 hrs
        if (secondRow != secondRowCount) {
            secondRowArray = mutableListOf()
            for (index in 0..3) {
                secondRowArray.add(if (index < secondRow) BoxColor.RED else BoxColor.WHITE)
            }
            secondRowCount = secondRow
        }

        return secondRowArray
    }

    /**
     * Returns first minutes row list
     *This function is used to determine first minutes row boxes colors
     * each box indicates 5 minutes
     */
    fun getThirdMinutesRow(minutes: Int): List<BoxColor> {
        val thirdRow = minutes / 5 // number of thirdRow indicates either RED or YELLOW color boxes
        // each 5 minutes

        //if thirdRowCount and thirdRow is same then this row needs no modification
        if (thirdRow != thirdRowCount) {
            thirdRowArray = mutableListOf()
            //Create 11 color boxes
            for (index in 0..10) {
                //Every 3rd box needs to be Red
                thirdRowArray.add(
                    if (index < thirdRow) (if (index % 3 == 2) BoxColor.RED else BoxColor.YELLOW)
                    else BoxColor.WHITE
                )
            }
            thirdRowCount = thirdRow
        }

        return thirdRowArray
    }

    /**
     * Returns 2nd minutes row list
     *This function is used to determine 2nd minutes row boxes colors
     * each box indicates 1 minutes
     */
    fun getFourthMinutesRow(minutes: Int): List<BoxColor> {
        val fourthRow = minutes % 5

        //if fourthRowCount and fourthRow is same then this row needs no modification
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