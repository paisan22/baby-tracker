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
import androidx.compose.ui.res.stringResource
import nl.paisan.babytracker.R
import nl.paisan.babytracker.data.entities.NutritionLog
import nl.paisan.babytracker.data.entities.NutritionLogWithDetails
import nl.paisan.babytracker.ui.common.BTconfirmDialog
import nl.paisan.babytracker.ui.common.BTdatetime
import nl.paisan.babytracker.ui.common.BTdatetimeDelta
import nl.paisan.babytracker.ui.screen.overviewActivity.overviews.shared.ListItemActions

@Composable
fun NutritionOverview(
    logs: List<NutritionLogWithDetails> = listOf(),
    onDelete: (log: NutritionLogWithDetails) -> Unit
) {
    Column(
        Modifier.verticalScroll(
            enabled = true,
            state = rememberScrollState()
        )
    ) {
        logs.forEach { log ->
            var showConfirmDialog by remember { mutableStateOf(false) }

            ListItem(
                overlineContent = { BTdatetime(datetime = log.nutritionLog.startTime) },
                headlineContent = { HeadlineContent(log = log) },
                supportingContent = { SupportingContent(log = log) },
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

@Composable
private fun HeadlineContent(log: NutritionLogWithDetails) {
    val nutritionType = when {
        log.bottleLog != null -> stringResource(R.string.noun_bottle)
        log.breastLog != null -> stringResource(R.string.noun_breast)
        else -> stringResource(R.string.sentence_nutrition_type_unknown)
    }

    val extraInfo = when {
        log.breastLog != null -> log.breastLog.breastSide.name
        log.bottleLog != null -> log.bottleLog.bottleType.typeName
        else -> ""
    }

    Text(text = "$nutritionType ($extraInfo)")
}

@Composable
private fun SupportingContent(log: NutritionLogWithDetails){
    return when {
        log.breastLog != null -> BTdatetimeDelta(
            start = log.nutritionLog.startTime,
            end = log.nutritionLog.endTime
        )
        log.bottleLog != null -> Column {
            BTdatetimeDelta(
                start = log.nutritionLog.startTime,
                end = log.nutritionLog.endTime
            )
            Text(text = "${log.bottleLog.millimeters} ml")
        }
        else -> Text(text = stringResource(R.string.sentence_no_extra_data_available))
    }
}