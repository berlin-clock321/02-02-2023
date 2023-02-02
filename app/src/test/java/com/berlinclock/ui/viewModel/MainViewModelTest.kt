package com.berlinclock.ui.viewModel

import com.berlinclock.ui.BoxColor
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test


internal class MainViewModelTest {
    private val berlinClock: MainViewModel = MainViewModel()

    @Test
    fun testFirstHoursRow() {
        val firstRowList1: MutableList<BoxColor> = mutableListOf(BoxColor.RED, BoxColor.RED, BoxColor.WHITE, BoxColor.WHITE)
        assertEquals(firstRowList1.toString(), berlinClock.getFirstRowHours(10).toString())
        val firstRowList2: MutableList<BoxColor> = mutableListOf(BoxColor.RED, BoxColor.RED, BoxColor.RED, BoxColor.RED)
        assertEquals(firstRowList2.toString(), berlinClock.getFirstRowHours(20).toString())
        val firstRowList3: MutableList<BoxColor> = mutableListOf(BoxColor.RED, BoxColor.RED, BoxColor.RED, BoxColor.RED)
        Assert.assertNotEquals(
            firstRowList3.toString(),
            berlinClock.getFirstRowHours(19).toString()
        )

        val firstRowList5: MutableList<BoxColor> = mutableListOf(BoxColor.RED, BoxColor.RED, BoxColor.WHITE, BoxColor.WHITE)
        assertEquals(firstRowList5.toString(), berlinClock.getFirstRowHours(12).toString())
    }

    @Test
    fun testSecondHoursRow() {
        val secondRowList1: MutableList<BoxColor> = mutableListOf(BoxColor.RED, BoxColor.RED, BoxColor.WHITE, BoxColor.WHITE)
        assertEquals(secondRowList1.toString(), berlinClock.getSecondRowHours(2).toString())
        val secondRowList2: MutableList<BoxColor> = mutableListOf(BoxColor.RED, BoxColor.RED, BoxColor.RED, BoxColor.RED)
        assertEquals(secondRowList2.toString(), berlinClock.getSecondRowHours(14).toString())
        val secondRowList3: MutableList<BoxColor> = mutableListOf(BoxColor.RED, BoxColor.RED, BoxColor.RED, BoxColor.RED)
        Assert.assertNotEquals(
            secondRowList3.toString(),
            berlinClock.getSecondRowHours(18).toString()
        )

        val secondRowList4: MutableList<BoxColor> = mutableListOf(BoxColor.RED, BoxColor.RED, BoxColor.WHITE, BoxColor.WHITE)
        assertEquals(secondRowList4.toString(), berlinClock.getSecondRowHours(12).toString())
    }

    @Test
    fun testThirdMinutesRow() {
        val thirdRowList1: MutableList<BoxColor> =
            mutableListOf(BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE)
        assertEquals(thirdRowList1.toString(), berlinClock.getThirdMinutesRow(2).toString())

        val thirdRowList2: MutableList<BoxColor> =
            mutableListOf(BoxColor.YELLOW, BoxColor.YELLOW, BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE)
        assertEquals(thirdRowList2.toString(), berlinClock.getThirdMinutesRow(10).toString())

        val thirdRowList3: MutableList<BoxColor> =
            mutableListOf(BoxColor.YELLOW, BoxColor.YELLOW, BoxColor.RED, BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE)
        assertEquals(thirdRowList3.toString(), berlinClock.getThirdMinutesRow(15).toString())

        val thirdRowList4: MutableList<BoxColor> =
            mutableListOf(BoxColor.YELLOW, BoxColor.YELLOW, BoxColor.RED, BoxColor.YELLOW, BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE)
        assertEquals(thirdRowList4.toString(), berlinClock.getThirdMinutesRow(22).toString())

        val thirdRowList5: MutableList<BoxColor> =
            mutableListOf(BoxColor.YELLOW, BoxColor.YELLOW, BoxColor.RED, BoxColor.YELLOW, BoxColor.YELLOW, BoxColor.RED, BoxColor.YELLOW, BoxColor.YELLOW, BoxColor.RED, BoxColor.YELLOW, BoxColor.YELLOW)
        assertEquals(thirdRowList5.toString(), berlinClock.getThirdMinutesRow(59).toString())
    }

    @Test
    fun testFourthMinutesRow() {
        val fourthRowList1: MutableList<BoxColor> = mutableListOf(BoxColor.YELLOW, BoxColor.YELLOW, BoxColor.WHITE, BoxColor.WHITE)
        assertEquals(fourthRowList1.toString(), berlinClock.getFourthMinutesRow(2).toString())
        val fourthRowList2: MutableList<BoxColor> = mutableListOf(BoxColor.YELLOW, BoxColor.YELLOW, BoxColor.YELLOW, BoxColor.YELLOW)
        assertEquals(fourthRowList2.toString(), berlinClock.getFourthMinutesRow(14).toString())
        val fourthRowList3: MutableList<BoxColor> = mutableListOf(BoxColor.YELLOW, BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE)
        Assert.assertNotEquals(
            fourthRowList3.toString(),
            berlinClock.getSecondRowHours(18).toString()
        )

        val fourthRowList4: MutableList<BoxColor> = mutableListOf(BoxColor.YELLOW, BoxColor.YELLOW, BoxColor.WHITE, BoxColor.WHITE)
        assertEquals(fourthRowList4.toString(), berlinClock.getFourthMinutesRow(12).toString())

        val fourthRowList5: MutableList<BoxColor> = mutableListOf(BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE, BoxColor.WHITE)
        assertEquals(fourthRowList5.toString(), berlinClock.getFourthMinutesRow(10).toString())

    }


}