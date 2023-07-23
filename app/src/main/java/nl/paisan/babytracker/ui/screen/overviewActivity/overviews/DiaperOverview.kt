package nl.paisan.babytracker.ui.screen.overviewActivity.overviews

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import nl.paisan.babytracker.data.entities.DiaperLog
import nl.paisan.babytracker.ui.common.BTconfirmDialog
import nl.paisan.babytracker.ui.common.BTdatetime
import nl.paisan.babytracker.ui.screen.overviewActivity.overviews.shared.ListItemActions

@Composable
fun DiaperOverview(logs: List<DiaperLog> = listOf(), onDelete: (log: DiaperLog) -> Unit) {
    Column(
        Modifier.verticalScroll(
            enabled = true,
            state = rememberScrollState()
        )
    ) {
        logs.forEach { log ->
            var showConfirmDialog by remember { mutableStateOf(false) }

            ListItem(
                overlineContent = { BTdatetime(datetime = log.start) },
                headlineContent = { Text(text = log.type.contentName) },
                supportingContent = { Text(text = log.note ?: "-") },
                trailingContent = { ListItemActions(onDelete = { showConfirmDialog = true }) }
            )
            Divider()

            if(showConfirmDialog) {
                BTconfirmDialog(
                    onYes = {
                        showConfirmDialog = false
                        onDelete(log)
                    },
                    onNo = { showConfirmDialog = false },
                )
            }
        }
    }
}