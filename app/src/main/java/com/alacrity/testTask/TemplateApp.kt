package com.alacrity.testTask

import android.app.Activity
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.alacrity.testTask.theme.AppTheme
import com.alacrity.testTask.ui.main.MainViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun TemplateApp(
    activity: Activity,
    homeViewModel: MainViewModel
) {
    AppTheme {
            val systemUiController = rememberSystemUiController()
            val darkIcons = MaterialTheme.colors.isLight
            SideEffect {
                systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = darkIcons)
            }

            AppNavGraph(
                activity = activity,
                homeViewModel = homeViewModel,
            )
        }

}

