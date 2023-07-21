package nl.paisan.babytracker.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import nl.paisan.babytracker.domain.services.getDateTime
import nl.paisan.babytracker.domain.services.getDelta

@Composable
fun BTtemporalData(start: Long, end: Long? = null, extraContent: @Composable () -> Unit = {}) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.wrapContentSize().padding(8.dp)
    ) {
        Text("Last time: ${context.getDateTime(start)}")

        end?.let { end ->
            val delta = context.getDelta(start, end)
            Text("Duration: $delta")
        }

        extraContent()
    }
}