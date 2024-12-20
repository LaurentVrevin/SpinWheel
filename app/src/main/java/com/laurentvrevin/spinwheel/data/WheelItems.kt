package com.laurentvrevin.spinwheel.data

import android.content.Context
import com.laurentvrevin.spinwheel.R

// Default items
fun getDefaultWheelItems(context: Context): MutableList<String> {
    return mutableListOf(
        context.getString(R.string.default_item_1),
        context.getString(R.string.default_item_2),
        context.getString(R.string.default_item_3),
        context.getString(R.string.default_item_4),
        context.getString(R.string.default_item_5),
        context.getString(R.string.default_item_6)
    )
}