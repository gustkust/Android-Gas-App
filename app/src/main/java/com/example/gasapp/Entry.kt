package com.example.gasapp

import java.io.Serializable

data class Entry(
    val id: Int,
    val user_id: Int,
    val type: String,
    val price: Double,
    val amount: Double,
    val date: String
) : Serializable