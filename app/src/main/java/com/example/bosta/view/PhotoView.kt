package com.example.bosta.view

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import coil.compose.AsyncImage

@Composable
fun ZoomableImage(photo:String) {
    val scale = remember { mutableStateOf(1f) }
    val rotationState = remember { mutableStateOf(0f) }
    Box(
        modifier = Modifier
            .clip(RectangleShape) // Clip the box content
            .fillMaxSize() // Give the size you want...
            .background(Color.White)
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, rotation ->
                    scale.value *= zoom
                    rotationState.value += rotation
                }
            }
    ) {
        AsyncImage(model = photo, contentDescription =null,
        modifier = Modifier
            .fillMaxSize()// keep the image centralized into the Box
            .graphicsLayer(
                // adding some zoom limits (min 50%, max 200%)
                scaleX = maxOf(.5f, minOf(3f, scale.value)),
                scaleY = maxOf(.5f, minOf(3f, scale.value)),
                rotationZ = rotationState.value
            ),)

    }
}