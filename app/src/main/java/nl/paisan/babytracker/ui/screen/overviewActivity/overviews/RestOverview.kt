package nl.paisan.babytracker.ui.screen.overviewActivity.overviews

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import nl.paisan.babytracker.data.entities.RestLog
import nl.paisan.babytracker.domain.services.getTime
import nl.paisan.babytracker.ui.common.BTdatetime
import nl.paisan.babytracker.ui.common.BTdatetimeDelta
import nl.paisan.babytracker.ui.screen.overviewActivity.overviews.shared.ListItemActions

@Composable
fun RestOverview(logs: List<RestLog> = listOf()) {
    Column(
        Modifier.verticalScroll(
            enabled = true,
            state = rememberScrollState()
        )
    ) {
        val context = LocalContext.current
        logs.forEach { log ->
            ListItem(
                overlineContent = { BTdatetime(datetime = log.start) },
                headlineContent = { Text(text = "${context.getTime(log.start)} - ${context.getTime(log.end)}") },
                supportingContent = { BTdatetimeDelta(start = log.start, end = log.end) },
                trailingContent = { ListItemActions(onDelete = {}) }
            )
            Divider()
        }
    }
}