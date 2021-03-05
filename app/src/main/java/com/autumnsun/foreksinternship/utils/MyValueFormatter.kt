package com.autumnsun.foreksinternship.utils

import android.util.Log
import com.autumnsun.foreksinternship.model.GraphModelItem
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class MyValueFormatter(
    private val graphItem: List<GraphModelItem>,
    private val xValsDateLabel: ArrayList<String>
) : ValueFormatter() {

    override fun getFormattedValue(value: Float): String? {
        val stringDate=ArrayList<String>()
        for (i in graphItem.indices) {
            val sdf = java.text.SimpleDateFormat("dd/MM/yyyy")
            val date = java.util.Date(value.toLong())
            val stringDate: String = sdf.format(date)
            Log.d("zaman", stringDate)
            xValsDateLabel.add(stringDate)
            return stringDate
        }
        return stringDate.toString()
    }

}
