package nl.paisan.babytracker.ui.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import nl.paisan.babytracker.domain.services.getDateTime

@Composable
fun BTdatetime(datetime: Long) {
    val context = LocalContext.current
    Text(context.getDateTime(datetime))
}