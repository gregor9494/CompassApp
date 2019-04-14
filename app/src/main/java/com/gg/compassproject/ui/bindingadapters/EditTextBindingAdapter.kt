package com.gg.compassproject.ui.bindingadapters

import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.gg.compassproject.ui.filters.ValueRangeInputFilter

class EditTextBindingAdapter {
    companion object {
        @BindingAdapter("minValue","maxValue")
        @JvmStatic
        fun bindMaxNumberValue(view: EditText,  minValue: Double,maxValue: Double) {
            view.filters = arrayOf(ValueRangeInputFilter(minValue, maxValue))
        }
    }
}