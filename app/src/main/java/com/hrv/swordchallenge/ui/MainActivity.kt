package com.hrv.swordchallenge.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import com.hrv.swordchallenge.ui.theme.SwordChallengeTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SwordChallengeTheme {
                mainViewModel.catBreeds.collectAsState().value.data?.forEach {
                    Log.d("Collect Breeds", "${it.name}")
                }
            }
        }
    }
}

