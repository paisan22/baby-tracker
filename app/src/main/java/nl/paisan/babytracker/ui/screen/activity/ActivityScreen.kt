package nl.paisan.babytracker.ui.screen.activity

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import nl.paisan.babytracker.ui.screen.ScreenWrapper

@Composable
fun ActvitivyScreen(
    navHostController: NavHostController,
    vm: ActivityViewModel = hiltViewModel()
) {
    ScreenWrapper(isLoading = vm.uiState.isLoading) {
        Text(text = "Activity screen")
    }
}