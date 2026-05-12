package com.example.doneapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.doneapp.ui.screens.SplashScreen
import com.example.doneapp.ui.screens.TasksScreen
import com.example.doneapp.ui.screens.TasksViewModel
import com.example.doneapp.ui.theme.AppTheme

class MainActivity : ComponentActivity() {

    private val tasksViewModel by viewModels<TasksViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var showAddDialog by remember { mutableStateOf(false) }
            val navController = rememberNavController()
            AppTheme {
                Scaffold(floatingActionButton = {
                    if (navController.currentBackStackEntryAsState().value?.destination?.route == "tasks")
                    FloatingActionButton(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                        onClick = {showAddDialog = !showAddDialog},
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Add, null)

                    }
                }, modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        NavHost(
                            navController,
                            startDestination = "splash"
                        ) {
                            composable("splash") { SplashScreen(navController) }
                            composable("tasks") { TasksScreen(showAddDialog,{ showAddDialog = it}, tasksViewModel) }

                        }


                    }
                }
            }
        }
    }

}