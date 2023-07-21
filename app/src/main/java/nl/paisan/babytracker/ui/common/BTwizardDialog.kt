package nl.paisan.babytracker.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BTwizardDialog(
    title: String,
    onClose: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(onDismissRequest = { onClose() }) {
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        shape = MaterialTheme.shapes.large,
        tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier
                .padding(4.dp)
                .fillMaxSize()
                .verticalScroll(enabled = true, state = rememberScrollState())
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        text = title,
                        style = TextStyle(
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
                content()
            }
        }
    }
}