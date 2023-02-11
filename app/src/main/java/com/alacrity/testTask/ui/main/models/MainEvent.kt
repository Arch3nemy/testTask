package com.alacrity.testTask.ui.main.models

import com.alacrity.testTask.BaseEvent
import com.alacrity.testTask.ui.main.MainViewModel

sealed class MainEvent: BaseEvent {

    object ButtonClicked : MainEvent()
    object EnterScreen : MainEvent()

}

fun MainViewModel.enterScreen() {
    obtainEvent(MainEvent.EnterScreen)
}

fun MainViewModel.buttonClicked() {
    obtainEvent(MainEvent.ButtonClicked)
}