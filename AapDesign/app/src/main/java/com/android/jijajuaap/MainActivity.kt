package com.android.jijajuaap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.android.jijajuaap.navigation.NavigationWrapper
import com.android.jijajuaap.ui.theme.JijajuAapTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {



            JijajuAapTheme {
                Surface(modifier = Modifier.fillMaxSize())
                {
                    NavigationWrapper()
                }
            }
        }
    }
}

