package com.alacrity.testTask.util

import android.app.IntentService
import android.content.Intent

class NotificationActionService: IntentService("NotificationActionService") {

    override fun onHandleIntent(p0: Intent?) {
        val action = p0?.action
    }

    companion object {
        const val ACTION_1 = "action_1"
        const val CHANNEL_ID = "42"
        const val NOTIFICATION_ID = 423
    }
}