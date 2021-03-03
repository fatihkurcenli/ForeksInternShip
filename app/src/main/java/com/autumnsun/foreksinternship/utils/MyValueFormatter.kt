package com.autumnsun.foreksinternship.utils

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter


class MyValueFormatter(private val xValsDateLabel: ArrayList<String>) : ValueFormatter() {

    override fun getFormattedValue(value: Float): String {
        return value.toString()
    }

    override fun getAxisLabel(value: Float, axis: AxisBase): String {
        if (value.toInt() >= 0 && value.toInt() <= xValsDateLabel.size - 1) {
            return xValsDateLabel[value.toInt()]
        } else {
            return ("").toString()
        }
    }
}