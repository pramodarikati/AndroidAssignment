package com.example.androidassignment.data.model


data class Service(
    val name: String,
    var isExpanded: Boolean = false,
    val iconResId: Int,
    val subServices: List<SubService>
)