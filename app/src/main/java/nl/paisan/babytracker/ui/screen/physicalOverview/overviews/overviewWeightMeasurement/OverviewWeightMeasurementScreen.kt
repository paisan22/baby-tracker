package nl.paisan.babytracker.ui.screen.physicalOverview.overviews.overviewWeightMeasurement

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
import androidx.hilt.navigation.compose.hiltViewModel
import nl.paisan.babytracker.ui.common.BTconfirmDialog
import nl.paisan.babytracker.ui.common.BTdatetime
import nl.paisan.babytracker.ui.screen.ScreenWrapper
import nl.paisan.babytracker.ui.screen.overviewActivity.overviews.shared.ListItemActions

@Composable
fun OverviewWeightMeasurementScreen(
    vm: OverviewWeightMeasurementViewModel = hiltViewModel()
) {
    ScreenWrapper(isLoading = vm.uisState.isLoading) {
        Column(modifier = Modifier.verticalScroll(
            enabled = true,
            state = rememberScrollState()
        )) {
            vm.uisState.measurements.forEach { measurement ->
                var showConfirmDialog by remember { mutableStateOf(false) }

                ListItem(
                    overlineContent = { BTdatetime(datetime = measurement.registrationDate) },
                    headlineContent = { Text(text = "${measurement.kilogram} KG") },
                    trailingContent = { ListItemActions(onDelete = { showConfirmDialog = true }) }
                )
                Divider()

                if(showConfirmDialog) {
                    BTconfirmDialog(
                        onYes = {
                            showConfirmDialog = false
                            vm.onDelete(measurement = measurement)
                        },
                        onNo = { showConfirmDialog = false },
                    )
                }
            }
        }
    }
}