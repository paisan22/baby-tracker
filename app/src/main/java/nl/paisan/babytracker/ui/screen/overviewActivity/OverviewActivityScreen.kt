package nl.paisan.babytracker.ui.screen.overviewActivity

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import nl.paisan.babytracker.R
import nl.paisan.babytracker.domain.enums.ActivityType
import nl.paisan.babytracker.ui.common.BTconfirmDialog
import nl.paisan.babytracker.ui.screen.ScreenWrapper
import nl.paisan.babytracker.ui.screen.overviewActivity.overviews.DiaperOverview
import nl.paisan.babytracker.ui.screen.overviewActivity.overviews.NutritionOverview
import nl.paisan.babytracker.ui.screen.overviewActivity.overviews.RestOverview

@Composable
fun OverviewActivityScreen(
    navHostController: NavHostController,
    vm: OverviewActivityViewModel = hiltViewModel()
) {
    ScreenWrapper(isLoading = vm.uiState.isLoading) {

        var state by remember { mutableStateOf(ActivityType.Nutrition) }
        val tabs = ActivityType.values().toList()
        val context = LocalContext.current

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
                ActivityType.Nutrition -> {
                    NutritionOverview(
                        logs = vm.uiState.nutritionLogs ?: listOf(),
                        onDelete = { log ->
                            vm.onDeleteNutritionLog(log = log)
                            notifyDeleted(context = context)
                        }
                    )
                }
                ActivityType.Rest -> {
                    RestOverview(
                        logs = vm.uiState.restLogs ?: listOf(),
                        onDelete = { log ->
                            vm.onDeleteRestLog(log = log)
                            notifyDeleted(context = context)
                        }
                    )
                }
                ActivityType.Diapers -> {
                    DiaperOverview(
                        logs = vm.uiState.diaperLogs ?: listOf(),
                        onDelete = { log ->
                            vm.onDeleteDiaperLog(log = log)
                            notifyDeleted(context = context)
                        }
                    )
                }
            }
        }

    }
}

private fun notifyDeleted(context: Context) {
    Toast.makeText(
        context,
        "Log deleted!",
        Toast.LENGTH_SHORT
    ).show()
}