package com.example.gasapp

import java.io.Serializable

data class PictureInfo(
    val author: String,
    val download_url: String,
    val height: Int,
    var id: String,
    val url: String,
    val width: Int
) : Serializable