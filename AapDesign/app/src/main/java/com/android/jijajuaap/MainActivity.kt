package com.android.jijajuaap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.android.jijajuaap.navigation.Navigationwrapper
import com.android.jijajuaap.presentationMvvm.MvvmPresentation
import com.android.jijajuaap.ui.theme.JijajuAapTheme

class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            navHostController= rememberNavController()
            val viewModel: MvvmPresentation = viewModel()

            JijajuAapTheme {
                Surface(modifier = Modifier.fillMaxSize())
                {
                    Navigationwrapper(navHostController,viewModel)
                }
            }
        }
    }
}

