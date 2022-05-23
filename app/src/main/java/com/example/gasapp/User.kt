package com.example.gasapp

import android.text.Editable
import java.io.Serializable

data class User(
    val id: Int,
    val name: String,
    val password: String,
    val picture: String
) : Serializable