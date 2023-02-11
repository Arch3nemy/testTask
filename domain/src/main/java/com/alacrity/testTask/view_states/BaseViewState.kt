package com.alacrity.testTask.view_states

sealed interface BaseViewState {



    fun getBaseState(): BaseViewState = Loading

    companion object {
        object Loading : BaseViewState
    }
}