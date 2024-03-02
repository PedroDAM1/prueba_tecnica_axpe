package com.pedropelayo.prueba_tecnica.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import com.pedropelayo.prueba_tecnica.ui.common.theme.PruebaTecnicaAxpeTheme
import com.pedropelayo.prueba_tecnica.ui.users.UserScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    private val vm : UsersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PruebaTecnicaAxpeTheme {
                Navigator(UserScreen())
            }
        }
    }
}
