package com.alacrity.testTask

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.ContactsContract
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.alacrity.testTask.entity.ButtonAction
import com.alacrity.testTask.ui.main.MainActivity.Companion.REQUEST_CODE
import com.alacrity.testTask.ui.main.MainViewModel
import com.alacrity.testTask.ui.main.models.enterScreen
import com.alacrity.testTask.ui.main.screen.HomeScreen
import com.alacrity.testTask.util.NotificationActionService
import com.alacrity.testTask.util.NotificationActionService.Companion.ACTION_1
import com.alacrity.testTask.util.NotificationActionService.Companion.CHANNEL_ID
import com.alacrity.testTask.util.NotificationActionService.Companion.NOTIFICATION_ID
import com.alacrity.testTask.util.hasContactPermission
import com.alacrity.testTask.util.requestContactPermission
import com.alacrity.testTask.view_states.MainViewState


/**
 * TODO remove hardcode, add valid days & cooldown features, notification action is not done yet
 */
@Composable
fun MainScreen(
    activity: Activity,
    viewModel: MainViewModel,
) {

    val state by viewModel.viewState.collectAsState()
    val actions by viewModel.actions.collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        createNotificationChannel(CHANNEL_ID, activity)
    }

    when (state) {
        MainViewState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                LinearProgressIndicator()
            }
        }
        is MainViewState.FinishedLoading -> {
            val action = chooseAction(actions)
            HomeScreen(
                isAnimationAvailable = (action.type == "animation"),
                onClick = { onButtonClick(actions, activity) })
        }
        is MainViewState.Error -> {
            /* ShowErrorView */
        }
        else -> Unit
    }

    LaunchedEffect(key1 = state, block = {
        viewModel.enterScreen()
    })

}

fun onButtonClick(actions: List<ButtonAction>, activity: Activity) {
    execute(activity, chooseAction(actions))
}

fun execute(activity: Activity, action: ButtonAction) {
    when (action.type) {
        "toast" -> {
            showToast(activity)
        }
        "call" -> {
            pickUpContact(activity)
        }
        "notification" -> {
            showNotification(activity, NOTIFICATION_ID)
        }
    }
}

fun chooseAction(actions: List<ButtonAction>): ButtonAction {
    var maxPriorityAction = ButtonAction("", true, 0, "", 0)
    actions.filter { it.enabled }.forEach {
        if (it.priority > maxPriorityAction.priority) maxPriorityAction = it
    }
    return maxPriorityAction
}

fun pickUpContact(activity: Activity) {
    if (hasContactPermission(activity)) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
        startActivityForResult(activity, intent, REQUEST_CODE, null)
    } else {
        requestContactPermission(activity, activity)
    }
}

fun showNotification(context: Context, notificationId: Int) {
    showSimpleNotification(
        context,
        CHANNEL_ID,
        notificationId,
        "Notification",
        "This is a simple notification with default priority."
    )
}

fun showToast(context: Context) {
    Toast.makeText(context, "Action is Toast", Toast.LENGTH_LONG).show()
}


fun createNotificationChannel(channelId: String, context: Context) {
    val name = "MyTestChannel"
    val descriptionText = "My important test channel"
    val importance = NotificationManager.IMPORTANCE_DEFAULT
    val channel = NotificationChannel(channelId, name, importance).apply {
        description = descriptionText
    }

    val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
}

fun showSimpleNotification(
    context: Context,
    channelId: String,
    notificationId: Int,
    textTitle: String,
    textContent: String,
    priority: Int = NotificationCompat.PRIORITY_DEFAULT
) {

    val action1Intent = Intent(context, NotificationActionService::class.java)
        .setAction(ACTION_1)

    val action1PendingIntent = PendingIntent.getService(
        context, 0,
        action1Intent, PendingIntent.FLAG_ONE_SHOT
    )

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.mipmap.ic_launcher_round)
        .setContentTitle(textTitle)
        .setContentText(textContent)
        .setPriority(priority)
        .addAction(NotificationCompat.Action(R.drawable.ic_launcher_background,
            "Open contacts action", action1PendingIntent));

    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notify(notificationId, builder.build())
    }
}
