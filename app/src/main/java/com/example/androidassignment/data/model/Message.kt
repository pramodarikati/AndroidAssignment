package com.example.androidassignment.data.model


data class Message(
    val tranId: String,
    val date: String,
    val resultScreenDetails: List<ResultScreenDetail>,
    val resultHeader: String,
    val resultScreenMessage: String
)