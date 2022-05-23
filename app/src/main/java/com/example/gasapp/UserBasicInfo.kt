package com.example.gasapp

import java.io.Serializable

data class UserBasicInfo(
    val name: String,
    val picture: String,
    val average_fuel_consumption: Double,
    val last_entry_date: String
) : Serializable