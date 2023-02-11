package com.alacrity.testTask.api

import com.alacrity.testTask.entity.ButtonAction
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("butto_to_action_config.json")
    fun getApiResponse(): Call<List<ButtonAction>>

}