package com.alacrity.testTask.repository

import com.alacrity.testTask.api.Api
import com.alacrity.testTask.entity.ButtonAction
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import javax.security.auth.callback.Callback
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class RepositoryImpl @Inject constructor(
    private val api: Api
) : Repository {

    override suspend fun getResponse(): List<ButtonAction> {
        return suspendCoroutine { continuation ->
            api.getApiResponse().enqueue(object : Callback, retrofit2.Callback<List<ButtonAction>> {
                override fun onResponse(
                    call: Call<List<ButtonAction>>,
                    response: Response<List<ButtonAction>>
                ) {
                    continuation.resumeWith(Result.success(response.body()!!))
                }

                override fun onFailure(call: Call<List<ButtonAction>>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }


}