package nl.paisan.babytracker.ui.screen.activity.wizards.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WizardStep(cards: List<@Composable () -> Unit>, supportiveText: String = "") {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
    ) {
        if(supportiveText.isNotEmpty()) { Text(supportiveText) }
        cards.forEach { card ->
            card()
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}