package nl.paisan.babytracker.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import nl.paisan.babytracker.domain.services.getDateTime
import nl.paisan.babytracker.domain.services.getDelta

@Composable
fun BTtemporalData(start: Long, end: Long) {
    val context = LocalContext.current

    Column {
        val delta = context.getDelta(start, end)
        Text("Last time: ${context.getDateTime(start)}")
        Text("Duration: $delta")
    }
}