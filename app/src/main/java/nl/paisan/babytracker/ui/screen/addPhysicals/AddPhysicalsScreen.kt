package nl.paisan.babytracker.ui.screen.addPhysicals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Scale
import androidx.compose.material.icons.outlined.Straighten
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import nl.paisan.babytracker.domain.enums.PhysicalType
import nl.paisan.babytracker.domain.enums.PhysicalType.Length
import nl.paisan.babytracker.domain.enums.PhysicalType.Weight
import nl.paisan.babytracker.domain.enums.PhysicalType.values
import nl.paisan.babytracker.ui.common.BTcardButton
import nl.paisan.babytracker.ui.common.BTcardColumn
import nl.paisan.babytracker.ui.screen.ScreenWrapper
import nl.paisan.babytracker.ui.screen.addPhysicals.addLength.AddLengthScreen
import nl.paisan.babytracker.ui.screen.addPhysicals.addWeight.AddWeightScreen

@Composable
fun AddPhysicalsScreen(
    navHostController: NavHostController,
    vm: AddPhysicalsViewModel = hiltViewModel()
) {
    ScreenWrapper(isLoading = vm.uiState.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val menuItems = listOf(
                Pair(Weight, Icons.Outlined.Scale),
                Pair(Length, Icons.Outlined.Straighten)
            )

            menuItems.forEach { item ->
                BTcardButton(
                    onClick = { vm.onPhysicalClick(item.first) },
                    label = item.first.name,
                    imageVector = item.second
                )
            }

            if(vm.uiState.showWeightWizard) {
                AddWeightScreen(navHostController = navHostController)
            }

            if(vm.uiState.showLengthWizard) {
                AddLengthScreen(navHostController = navHostController)
            }
        }
    }
}