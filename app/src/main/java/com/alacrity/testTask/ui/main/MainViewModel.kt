package com.alacrity.testTask.ui.main

import com.alacrity.testTask.entity.ButtonAction
import com.alacrity.testTask.ui.main.models.MainEvent
import com.alacrity.testTask.use_cases.GetSimpleResponseUseCase
import com.alacrity.testTask.util.BaseViewModel
import com.alacrity.testTask.view_states.MainViewState
import com.alacrity.testTask.view_states.MainViewState.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getSimpleResponseUseCase: GetSimpleResponseUseCase
) : BaseViewModel<MainEvent, MainViewState>(Loading) {

    val viewState: StateFlow<MainViewState> = _viewState
    val actions = MutableSharedFlow<List<ButtonAction>>()

    override fun obtainEvent(event: MainEvent) {
        when (val currentState = _viewState.value) {
            is Loading -> currentState.reduce(event)
            is Error -> currentState.reduce(event)
            is FinishedLoading -> currentState.reduce(event)
        }
    }

    private fun Loading.reduce(event: MainEvent) {
        logReduce(event)
        when (event) {
            MainEvent.EnterScreen -> {
                getSimpleResponse()
            }
            else -> Unit
        }
    }

    private fun Error.reduce(event: MainEvent) {
        logReduce(event)

    }

    private fun FinishedLoading.reduce(event: MainEvent) {
        logReduce(event)
        when (event) {
            is MainEvent.ButtonClicked -> {

            }
            else -> Unit
        }
    }

    private fun getSimpleResponse() {
        launch(
            logError = "Error Getting response",
            logSuccess = "Successfully received response",
            onSuccess = { actionList ->
                launch {
                    actions.emit(actionList)
                    _viewState.value = FinishedLoading.also {
                        Timber.d("response title ${actionList.size}")
                    }
                }
            },
            onFailure = {
                _viewState.value = Error(it).also { exception ->
                    Timber.d("Error:: $exception")
                }
            }
        ) {
            getSimpleResponseUseCase()
        }

    }

}