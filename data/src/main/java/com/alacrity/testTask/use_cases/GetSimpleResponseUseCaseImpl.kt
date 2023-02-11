package com.alacrity.testTask.use_cases

import com.alacrity.testTask.repository.Repository
import javax.inject.Inject

class GetSimpleResponseUseCaseImpl @Inject constructor(
    private val repository: Repository
): GetSimpleResponseUseCase {

    override suspend fun invoke() = repository.getResponse()

}