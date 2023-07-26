package nl.paisan.babytracker.ui.screen.addActivity

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.BabyChangingStation
import androidx.compose.material.icons.outlined.BedroomBaby
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.Feed
import androidx.compose.material.icons.outlined.LocalDrink
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.SportsBar
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import nl.paisan.babytracker.R
import nl.paisan.babytracker.domain.enums.ActivityType
import nl.paisan.babytracker.domain.enums.BottleType
import nl.paisan.babytracker.ui.common.BTcardButton
import nl.paisan.babytracker.ui.screen.ScreenWrapper
import nl.paisan.babytracker.ui.screen.addActivity.wizards.DiaperWizard
import nl.paisan.babytracker.ui.screen.addActivity.wizards.NutritionWizard
import nl.paisan.babytracker.ui.screen.addActivity.wizards.RestWizard

@Composable
fun AddActvitivyScreen(
    navHostController: NavHostController,
    vm: AddActivityViewModel = hiltViewModel()
) {
    ScreenWrapper(isLoading = vm.uiState.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val context = LocalContext.current

            val menuItems = listOf(
                Pair(ActivityType.Nutrition, Icons.Outlined.Restaurant),
                Pair(ActivityType.Rest, Icons.Outlined.Bedtime),
                Pair(ActivityType.Diapers, Icons.Outlined.BabyChangingStation),
            )

            menuItems.forEach { item ->
                BTcardButton(
                    onClick = { vm.onActivityClick(activityType = item.first) },
                    label = item.first.name,
                    imageVector = item.second
                )
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
