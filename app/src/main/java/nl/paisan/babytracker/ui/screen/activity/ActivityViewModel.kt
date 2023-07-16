package nl.paisan.babytracker.ui.screen.activity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(): ViewModel() {
    var uiState by mutableStateOf(ActivityUiState())
        private set

    init {
        viewModelScope.launch {
            delay(2000)
            uiState = uiState.copy(
                isLoading = false
            )
        }
    }
}