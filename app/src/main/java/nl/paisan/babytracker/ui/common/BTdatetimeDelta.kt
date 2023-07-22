package nl.paisan.babytracker.ui.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import nl.paisan.babytracker.domain.services.getDelta

@Composable
fun BTdatetimeDelta(start: Long, end: Long) {
    val context = LocalContext.current
    val delta = context.getDelta(startDate = start, endDate = end)
    Text(text = "$delta (min:sec)")
}