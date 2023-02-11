package com.alacrity.testTask.use_cases

import com.alacrity.testTask.entity.ButtonAction

interface GetSimpleResponseUseCase {

    suspend operator fun invoke(): List<ButtonAction>

}