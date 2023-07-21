package nl.paisan.babytracker.ui.screen.activity

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import nl.paisan.babytracker.domain.enums.ActivityType
import nl.paisan.babytracker.domain.enums.BottleType
import nl.paisan.babytracker.ui.screen.ScreenWrapper
import nl.paisan.babytracker.ui.screen.activity.wizards.NutritionWizard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActvitivyScreen(
    navHostController: NavHostController,
    vm: ActivityViewModel = hiltViewModel()
) {
    ScreenWrapper(isLoading = vm.uiState.isLoading) {

        val activityTypes = ActivityType
            .values()
            .toList()
            .map { it.name }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
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
                    onClose = { vm.onCloseActivity(activityType = ActivityType.Nutrition) },
                    addBreastLog = { start, end, breastSide ->
                        vm.addNutritionBreastLog(start, end, breastSide)
                        vm.onCloseActivity(activityType = ActivityType.Nutrition)
                    },
                    addBottleLog = { start: Long, end: Long, bottleType: BottleType, milliliters: Int ->
                        vm.addNutritionBottleLog(
                            start = start,
                            end = end,
                            bottleType = bottleType,
                            milliliters = milliliters
                        )

                        vm.onCloseActivity(activityType = ActivityType.Nutrition)
                    },
                    lastBreastLog = vm.uiState.lastBreastLog,
                    lastBottleLog = vm.uiState.lastBottleLog,
                )
            }
        }
    }
}

