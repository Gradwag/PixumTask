package com.pixum.pixumtask.view.description

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pixum.pixumtask.view.description.ui.theme.PixumTaskTheme

class DescriptionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PixumTaskTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            10.dp
                        ),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Title()
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        ComicDescription()
                    }
                }
            }
        }
    }
}

@Composable
fun Title() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Description",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun ComicDescription() {
    val context = LocalContext.current
    val intent = (context as DescriptionActivity).intent
    var comicDescription = intent.getStringExtra("description")

    if (comicDescription.isNullOrEmpty())
        comicDescription = "No description"

    Text(
        modifier = Modifier.fillMaxWidth(),
        text = comicDescription,
        fontSize = 19.sp,
    )
}

@Preview(showBackground = true)
@Composable
fun DescriptionActivityPreview() {
    PixumTaskTheme {
        DescriptionActivity()
    }
}