package nl.paisan.babytracker.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BTbutton(
    name: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    Button(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = { onClick() },
        enabled = enabled
    ) { Text(name) }
}