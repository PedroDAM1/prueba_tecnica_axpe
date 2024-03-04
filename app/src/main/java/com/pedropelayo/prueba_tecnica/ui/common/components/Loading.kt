package com.pedropelayo.prueba_tecnica.ui.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingCard(
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        CircularProgressIndicator(
            modifier = Modifier
                .background(color = Color.Transparent)
                .padding(24.dp)
        )
    }
}