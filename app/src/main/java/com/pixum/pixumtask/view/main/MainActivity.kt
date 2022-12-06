package com.pixum.pixumtask.view.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.pixum.pixumtask.ui.theme.PixumTaskTheme
import com.pixum.pixumtask.view.common.composables.TableItemComposable
import com.pixum.pixumtask.viewmodel.main.MainUIModel
import com.pixum.pixumtask.viewmodel.main.MainUiState
import com.pixum.pixumtask.viewmodel.main.MainViewModel
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PixumTaskTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    viewModel = ViewModelProvider(this)[MainViewModel::class.java]
                    viewModel.getComics()
                    LoadComics(viewModel)
                }
            }
        }
    }
}

@Composable
fun LoadComics(viewModel: MainViewModel) {
    when (val state = viewModel.uiState.collectAsState().value) {
        is MainUiState.Empty -> Text(
            text = "no data available",
            modifier = Modifier.padding(16.dp)
        )
        is MainUiState.Loading ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        is MainUiState.Error -> ErrorDialog(state.message)
        is MainUiState.Loaded -> ComicsLoadedScreen(
            viewModel = viewModel,
            data = state.data
        )
    }
}

@Composable
fun ComicsLoadedScreen(
    viewModel: MainViewModel,
    data: MainUIModel
) {
    val context = LocalContext.current
    var refreshing by remember { mutableStateOf(false) }

    LaunchedEffect(refreshing) {
        if (refreshing) {
            delay(1000)
            refreshing = false
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = refreshing),
        onRefresh = {
            refreshing = true
            viewModel.getComics()
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray),
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 8.dp
            )) {
            items(data.data?.results!!.size) {
                TableItemComposable(
                    onComicClick = {
                        viewModel.onComicClicked(
                            context,
                            data.data.results[it].description.toString()
                        )
                    },
                    title = data.data.results[it].title.toString(),
                    thumbnailUrl = data.data.results[it].thumbnail?.path.toString()
                )
            }
        }
    }
}

@Composable
fun ErrorDialog(message: String) {
    Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    PixumTaskTheme {
        MainActivity()
    }
}