package com.alacrity.testTask.exceptions

class NoDataFromResponseException(message: String = "Undefined", exception: Throwable? = null) : TemplateException(message, exception)