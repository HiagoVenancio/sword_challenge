package com.hrv.swordchallenge.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.navigation.compose.rememberNavController
import com.hrv.swordchallenge.ui.navigation.SetupNavGraph
import com.hrv.swordchallenge.ui.theme.SwordChallengeTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SwordChallengeTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Cat Breeds") },
                            actions = {
                                IconButton(onClick = {
                                    navController.navigate("favorite_cats")
                                }) {
                                    Icon(Icons.Default.Favorite, contentDescription = "Favorites")
                                }
                            }
                        )
                    }
                ) { paddingValues ->
                    SetupNavGraph(
                        navController = navController,
                        viewModel = mainViewModel,
                        paddingValues = paddingValues
                    )
                }
            }
        }
    }
}


