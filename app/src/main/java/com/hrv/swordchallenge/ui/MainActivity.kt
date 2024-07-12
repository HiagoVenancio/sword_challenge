package com.hrv.swordchallenge.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hrv.swordchallenge.ui.screen.CatBreedsListScreen
import com.hrv.swordchallenge.ui.screen.CatBreedDetailScreen
import com.hrv.swordchallenge.ui.theme.SwordChallengeTheme
import com.hrv.swordchallenge.ui.navigation.Screen
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SwordChallengeTheme {
                val navController = rememberNavController()
                SetupNavGraph(navController = navController, viewModel = mainViewModel)
            }
        }
    }
}

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.CatBreedsList.route
    ) {
        composable(route = Screen.CatBreedsList.route) {
            CatBreedsListScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            route = Screen.CatBreedDetail.route + "/{breedId}",
            arguments = listOf(navArgument("breedId") { type = NavType.StringType })
        ) { backStackEntry ->
            CatBreedDetailScreen(viewModel = viewModel, backStackEntry = backStackEntry)
        }
    }
}
