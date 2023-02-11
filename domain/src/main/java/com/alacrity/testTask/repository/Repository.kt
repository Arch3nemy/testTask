package com.alacrity.testTask.repository

import com.alacrity.testTask.entity.ButtonAction

interface Repository {
    suspend fun getResponse(): List<ButtonAction>

}