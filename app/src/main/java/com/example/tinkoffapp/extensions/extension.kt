package com.example.tinkoffapp.extensions

import android.content.Context
import android.util.TypedValue

fun Context.dpToPxInt(dp: Int): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    dp.toFloat(),
    this.resources.displayMetrics
).toInt()