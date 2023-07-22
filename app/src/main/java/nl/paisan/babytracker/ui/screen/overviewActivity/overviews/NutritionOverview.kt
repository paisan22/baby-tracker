package nl.paisan.babytracker.ui.screen.overviewActivity.overviews

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import nl.paisan.babytracker.data.entities.NutritionLogWithDetails
import nl.paisan.babytracker.ui.common.BTdatetime
import nl.paisan.babytracker.ui.common.BTdatetimeDelta
import nl.paisan.babytracker.ui.common.BTtemporalData

@Composable
fun NutritionOverview(logs: List<NutritionLogWithDetails> = listOf()) {
    Column {
        logs.forEach { log ->
            val nutritionType = if(log.bottleLog != null) {
                "Bottle"
            } else if(log.breastLog != null) {
                "Breast"
            } else {
                "nutrition type unknown"
            }

            val extraHeadInfo = if(log.breastLog != null) {
                log.breastLog.breastSide.name
            } else if (log.bottleLog != null) {
                log.bottleLog.bottleType.typeName
            } else {
                ""
            }

            ListItem(
                overlineContent = { BTdatetime(datetime = log.nutritionLog.startTime) },
                headlineContent = { Text(text = "$nutritionType ($extraHeadInfo)") },
                supportingContent = {
                    if(log.breastLog != null) {
                        BTdatetimeDelta(
                            start = log.nutritionLog.startTime,
                            end = log.nutritionLog.endTime
                        )
                    } else if (log.bottleLog != null) {
                        Column {
                            BTdatetimeDelta(
                                start = log.nutritionLog.startTime,
                                end = log.nutritionLog.endTime
                            )
                            Text(text = "${log.bottleLog.millimeters} ml")
                        }
                    } else {
                        Text(text = "No extra data available")
                    }
                },
                trailingContent = { IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
                } }
            )
        }
    }
}