package com.alacrity.testTask

interface EventHandler<T> {
    fun obtainEvent(event: T)
}