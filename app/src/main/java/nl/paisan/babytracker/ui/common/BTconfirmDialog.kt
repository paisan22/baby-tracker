package nl.paisan.babytracker.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import nl.paisan.babytracker.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BTconfirmDialog(
    onYes: () -> Unit,
    onNo: () -> Unit,
    question: String = stringResource(R.string.confirm_are_you_sure)
) {
    AlertDialog(onDismissRequest = { onNo() }) {
        Surface(modifier = Modifier
            .wrapContentSize()
            .padding(16.dp),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(
                Modifier
                    .wrapContentSize()
                    .padding(16.dp)) {
                Text(text = question)
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(
                        modifier = Modifier
                            .wrapContentSize(),
                        onClick = { onNo() },
                        enabled = true
                    ) { Text(text = stringResource(R.string.no)) }

                    Spacer(modifier = Modifier.width(4.dp))

                    Button(
                        modifier = Modifier
                            .wrapContentSize(),
                        onClick = { onYes() },
                        enabled = true
                    ) { Text(text = stringResource(R.string.yes)) }
                }
            }
        }
    }
}