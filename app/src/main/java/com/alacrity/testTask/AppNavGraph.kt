package com.alacrity.testTask

import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alacrity.testTask.Destinations.HOME_ROUTE
import com.alacrity.testTask.ui.main.MainViewModel

object Destinations {
    const val HOME_ROUTE = "home"
}

private val permissionsRequired = arrayOf(
    android.Manifest.permission.CALL_PHONE,
    android.Manifest.permission.READ_CONTACTS
)

private val askPermissions = arrayListOf<String>()

@Composable
fun AppNavGraph(
    activity: Activity,
    homeViewModel: MainViewModel,
    navController: NavHostController = rememberNavController(),
    startDestination: String = HOME_ROUTE,
) {
    var allPermissionGranted by remember {
        mutableStateOf(false)
    }
    val permissionsLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) { permissionsMap ->
            allPermissionGranted = !permissionsMap.containsValue(false)
        }

    LaunchedEffect(key1 = Unit) {
        permissionsLauncher.launch(askPermissions.toTypedArray())
    }

    for (permission in permissionsRequired) {
        if (ContextCompat.checkSelfPermission(
                activity,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            askPermissions.add(permission)
        }
    }
    allPermissionGranted = askPermissions.isEmpty()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(HOME_ROUTE) {
            if(allPermissionGranted)
            MainScreen(
                activity = activity,
                viewModel = homeViewModel,
            )
        }
    }
}