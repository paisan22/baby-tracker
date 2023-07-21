package nl.paisan.babytracker.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BTwizardDialog(
    onClose: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(onDismissRequest = { onClose() }) {
        Surface(modifier = Modifier
            .fillMaxSize(),
        shape = MaterialTheme.shapes.large,
        tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier
                .padding(4.dp)
                .fillMaxSize()
            ) {
                content()
            }
        }
    }
}