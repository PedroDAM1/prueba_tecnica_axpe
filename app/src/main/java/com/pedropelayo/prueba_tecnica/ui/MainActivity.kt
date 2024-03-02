package com.pedropelayo.prueba_tecnica.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cafe.adriel.voyager.navigator.Navigator
import com.pedropelayo.prueba_tecnica.ui.common.theme.PruebaTecnicaAxpeTheme
import com.pedropelayo.prueba_tecnica.ui.users.UserScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PruebaTecnicaAxpeTheme {
                Navigator(UserScreen())
            }
        }
    }
}
