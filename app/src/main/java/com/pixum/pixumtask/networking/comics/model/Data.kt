package com.pixum.pixumtask.networking.comics.model

import com.google.gson.annotations.SerializedName

data class Data(
    val limit: Int,
    @SerializedName("results")
    val results: List<Comic>? = null
)
