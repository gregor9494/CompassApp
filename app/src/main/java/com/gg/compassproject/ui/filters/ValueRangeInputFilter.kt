package com.gg.compassproject.ui.filters

import android.text.InputFilter
import android.text.Spanned


class ValueRangeInputFilter(private val minValue: Double, private val maxValue: Double) : InputFilter {

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dStart: Int,
        dEnd: Int
    ): CharSequence? {
        try {
            val text = dest.toString() + source.toString()
            if (text == "-") return null
            val input = (text).toDouble()
            if (input in minValue..maxValue) {
                return null
            }
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        return ""
    }
}