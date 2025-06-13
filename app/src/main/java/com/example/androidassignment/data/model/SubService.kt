package com.example.androidassignment.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubService(
    val name: String,
    val iconResId: Int,
    var isSelected: Boolean = false
) : Parcelable