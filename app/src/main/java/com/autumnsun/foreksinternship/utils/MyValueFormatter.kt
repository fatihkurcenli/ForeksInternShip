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
    /* override fun getFormattedValue(value: Float): String {
         return value.toString()
     }

     override fun getAxisLabel(value: Float, axis: AxisBase): String {
         return xValsDateLabel.toString()
     }*/

    private val mFormat: SimpleDateFormat = SimpleDateFormat("dd MMM HH:mm", Locale.ENGLISH)
    override fun getFormattedValue(value: Float): String? {
        val stringDate=ArrayList<String>()
        for (i in graphItem.indices) {
            val sdf = java.text.SimpleDateFormat("MM/dd/yyyy")
            val date = java.util.Date(value.toLong())
            val stringDate: String = sdf.format(date)
            Log.d("zaman", stringDate)
            xValsDateLabel.add(stringDate)
            return stringDate
        }
        return stringDate.toString()
    }
    /*override fun getAxisLabel(value: Float, axis: AxisBase): String {
        return xValsDateLabel.toString()
    } */
}
