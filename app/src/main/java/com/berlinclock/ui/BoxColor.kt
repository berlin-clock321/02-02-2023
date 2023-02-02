package com.berlinclock.ui

sealed class BoxColor(private val str: String) {
    object RED : BoxColor("R")
    object YELLOW : BoxColor("Y")
    object WHITE : BoxColor("W")

    override fun toString(): String {
        return str
    }
}
