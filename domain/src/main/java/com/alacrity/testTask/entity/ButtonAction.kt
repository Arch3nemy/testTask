package com.alacrity.testTask.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ButtonAction(
    @Json(name = "type") var type: String,
    @Json(name = "enabled") var enabled: Boolean,
    @Json(name = "priority") var priority: Int,
    @Json(name = "valid_days") var validDays: String,
    @Json(name = "cool_down") var coolDown: Int
)