package com.alacrity.testTask.ui.main.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(isAnimationAvailable: Boolean, onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        var flag by remember { mutableStateOf(true) }
        val rotation = remember { Animatable(0f) }
        val scope = rememberCoroutineScope()

        Column(Modifier.padding(30.dp)) {
            Button(
                onClick = {
                    onClick()
                    if (isAnimationAvailable)
                        scope.launch {
                            rotation.animateTo(
                                targetValue = 180f,
                                animationSpec = tween(1000, easing = LinearEasing)
                            )
                            flag = !flag
                            rotation.animateTo(
                                targetValue = 360f,
                                animationSpec = tween(1000, easing = LinearEasing)
                            )
                            rotation.snapTo(0f)
                        }
                },
                modifier = Modifier
                    .rotate(rotation.value)
            ) {
                Text(text = "Click Me")
            }
        }
    }
}