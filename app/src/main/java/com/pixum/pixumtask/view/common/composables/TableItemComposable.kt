package com.pixum.pixumtask.view.common.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun TableItemComposable(
    onComicClick: () -> Unit,
    title: String,
    thumbnailUrl: String
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(
            top = 10.dp,
            bottom = 10.dp
        )
        .background(Color.White)
        .clickable(onClick = onComicClick)
    ) {
        Text(
            modifier = Modifier.padding(5.dp),
            text = title,
            fontSize = 21.sp
        )

        Image(
            modifier = Modifier
                .padding(5.dp)
                .size(130.dp)
                .background(Color.LightGray),
            painter = rememberAsyncImagePainter(thumbnailUrl),
            contentDescription = "Comic thumbnail"
        )
    }
}