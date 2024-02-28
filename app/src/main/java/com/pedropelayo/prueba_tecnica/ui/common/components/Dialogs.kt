package com.pedropelayo.prueba_tecnica.ui.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.pedropelayo.prueba_tecnica.R

@Composable
fun InputDialog(
    title : String,
    label : String = "",
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var textFieldValue by remember { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            properties = DialogProperties(),
            onDismissRequest = onDismiss,
            title = { Text(text = title) },
            text = {
                Column {
                    OutlinedTextField(
                        value = textFieldValue,
                        onValueChange = { textFieldValue = it },
                        label = { Text(text = label, color = Color.Black) }
                    )
                }
            },
            confirmButton = {
                Button(onClick = { onConfirm(textFieldValue) }) {
                    Text(stringResource(R.string.confirm))
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}