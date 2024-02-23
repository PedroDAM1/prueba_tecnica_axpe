package com.pedropelayo.prueba_tecnica.ui.users

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pedropelayo.prueba_tecnica.ui.common.theme.PruebaTecnicaAxpeTheme

@Composable
fun UsersScreen(){
    //userViewModel : UsersViewModel = viewModel()
}

@Preview
@Composable
fun UserScreenPreview(){
    PruebaTecnicaAxpeTheme {
        UsersScreen()
    }
}