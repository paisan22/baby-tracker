package nl.paisan.babytracker.ui.screen.activity.wizards.shared

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WizardStepCard(onClick: () -> Unit, text: String) {
    Card(
        onClick = { onClick() },
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(120.dp)) {
            Text(text, Modifier.align(Alignment.Center))
        }
    }
}