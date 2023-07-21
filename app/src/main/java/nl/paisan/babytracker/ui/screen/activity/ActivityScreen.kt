package nl.paisan.babytracker.ui.screen.activity

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import nl.paisan.babytracker.R
import nl.paisan.babytracker.domain.enums.ActivityType
import nl.paisan.babytracker.domain.enums.BottleType
import nl.paisan.babytracker.ui.screen.ScreenWrapper
import nl.paisan.babytracker.ui.screen.activity.wizards.DiaperWizard
import nl.paisan.babytracker.ui.screen.activity.wizards.NutritionWizard
import nl.paisan.babytracker.ui.screen.activity.wizards.RestWizard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActvitivyScreen(
    navHostController: NavHostController,
    vm: ActivityViewModel = hiltViewModel()
) {
    ScreenWrapper(isLoading = vm.uiState.isLoading) {
        val activityTypes = ActivityType.values()
            .toList()
            .map { it.name }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val context = LocalContext.current

            activityTypes.forEach { activityType ->
                Card(
                    onClick = { vm.onActivityClick(activityType = ActivityType.valueOf(activityType)) },
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(180.dp)) {
                        Text(activityType, Modifier.align(Alignment.Center))
                    }
                }
            }

            if(vm.uiState.showNutritionWizard) {
                NutritionWizard(
                    onClose = { vm.onCloseActivity() },
                    addBreastLog = { start, end, breastSide ->
                        vm.addNutritionBreastLog(start, end, breastSide)
                        vm.onCloseActivity()
                    },
                    addBottleLog = { start: Long, end: Long, bottleType: BottleType, milliliters: Int ->
                        vm.addNutritionBottleLog(
                            start = start,
                            end = end,
                            bottleType = bottleType,
                            milliliters = milliliters
                        )

                        vm.onCloseActivity()
                        notifySaved(context)
                    },
                    lastBreastLog = vm.uiState.lastBreastLog,
                    lastBottleLog = vm.uiState.lastBottleLog,
                )
            }
            
            if(vm.uiState.showRestWizard) {
                RestWizard(
                    onClose = { vm.onCloseActivity() },
                    addRestLog = { start, end ->
                        vm.addRestLog(start = start, end = end)
                        vm.onCloseActivity()
                        notifySaved(context)
                    },
                    lastLog = vm.uiState.lastRestLog
                )
            }
            
            if(vm.uiState.showDiapersWizard) {
                DiaperWizard(
                    onClose = { vm.onCloseActivity() },
                    addDiaperLog = { start, type, note ->
                        vm.addDiaperLog(start, type, note)
                        vm.onCloseActivity()
                        notifySaved(context)
                    },
                    lastLog = vm.uiState.lastDiaperLog
                )
            }
        }
    }
}

private fun notifySaved(context: Context) {
    Toast.makeText(
        context,
        context.getString(R.string.action_activity_added),
        Toast.LENGTH_SHORT
    ).show()
}
