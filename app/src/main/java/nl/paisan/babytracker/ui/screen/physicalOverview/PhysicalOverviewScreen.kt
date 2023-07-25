package nl.paisan.babytracker.ui.screen.physicalOverview

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextOverflow
import nl.paisan.babytracker.ui.screen.physicalOverview.overviews.overviewWeightMeasurement.OverviewWeightMeasurementScreen

@Composable
fun PhysicalOverviewScreen() {
    var state by remember { mutableStateOf(PhysicalType.Weight) }
    val tabs = PhysicalType.values().toList()
    
    Column {
        TabRow(selectedTabIndex = state.ordinal) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = state == tabs[index],
                    onClick = { state = tabs[index] },
                    text = { Text(text = title.name, maxLines = 2, overflow = TextOverflow.Ellipsis) }
                )
            }
        }
        
        when(state) {
            PhysicalType.Weight -> {
                OverviewWeightMeasurementScreen()
            }
            PhysicalType.Length -> {
                Text(text = "Not implemented yet.")
            }
        }
    }
}


private enum class PhysicalType {
    Weight,
    Length
}
