package com.pixum.pixumtask.viewmodel.main

sealed class MainUiState {
    object Empty : MainUiState()
    object Loading : MainUiState()
    class Loaded(val data: MainUIModel) : MainUiState()
    class Error(val message: String) : MainUiState()
}