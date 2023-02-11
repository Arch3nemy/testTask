package com.alacrity.testTask.view_states


sealed class MainViewState: BaseViewState {
    object Loading : MainViewState()
    data class Error(val exception: Throwable? = null, val message: String = "") : MainViewState()
    object FinishedLoading : MainViewState()
}