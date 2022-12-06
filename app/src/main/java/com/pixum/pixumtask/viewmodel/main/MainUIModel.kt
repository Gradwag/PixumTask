package com.pixum.pixumtask.viewmodel.main

import com.pixum.pixumtask.networking.comics.model.Data

data class MainUIModel(
    val copyright: String,
    val data: Data? = null
)