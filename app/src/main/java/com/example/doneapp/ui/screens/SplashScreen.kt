package com.example.doneapp.ui.screens

import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.doneapp.R
import com.example.doneapp.data.models.Task
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {

    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("tasks")
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(R.drawable.logo),
            null,
            modifier = Modifier.fillMaxSize(0.5f),
        )



    }
}