package nl.paisan.babytracker.ui.screen.bio

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nl.paisan.babytracker.domain.entities.Bio
import nl.paisan.babytracker.domain.enums.Gender
import nl.paisan.babytracker.domain.repositories.IBioRepo
import javax.inject.Inject

@HiltViewModel
class BioViewModel @Inject constructor(
    private val bioRepo: IBioRepo
): ViewModel() {
    private val TAG = "BioViewModel"

    var uiState by mutableStateOf(BioScreenUiState())
        private set

    init { observeData() }

    fun onSave() {
        viewModelScope.launch {
            bioRepo.insertBio(data = Bio(
                id = uiState.bio.id,
                name = uiState.newBio.name,
                birthday = uiState.newBio.birthday,
                gender = uiState.newBio.gender,
                firstLength = uiState.newBio.length,
                firstWeight = uiState.newBio.weight
            ))

            uiState = uiState.copy(
                hasUpdates = false
            )
        }
    }

    fun onNameChange(name: String) {
        uiState = uiState.copy(
            newBio = uiState.newBio.copy(
                name = name
            ),
            hasUpdates = true
        )
    }

    fun showDatePicker(show: Boolean) {
        uiState = uiState.copy(
            showDatePicker = show
        )
    }

    fun onDateSelection(date: Long) {
        uiState = uiState.copy(
            newBio = uiState.newBio.copy(
                birthday = date
            ),
            hasUpdates = true
        )
    }

    fun onGenderSelection(gender: Gender) {
        uiState = uiState.copy(
            newBio = uiState.newBio.copy(
                gender = gender
            ),
            hasUpdates = true
        )
    }

    fun onWeightChange(weight: Int) {
        uiState = uiState.copy(
            newBio = uiState.newBio.copy(
                weight = weight
            ),
            hasUpdates = true
        )
    }

    fun onLengthChange(length: Int) {
        uiState = uiState.copy(
            newBio = uiState.newBio.copy(
                length = length
            ),
            hasUpdates = true
        )
    }

    private fun observeData() {
        viewModelScope.launch {
            bioRepo.getBio.collect { bio ->
                uiState = if (bio != null) {
                    val bioUiState = BioUiState(
                        id = bio.id,
                        name = bio.name,
                        birthday = bio.birthday,
                        gender = bio.gender,
                        length = bio.firstLength,
                        weight = bio.firstWeight
                    )
                    uiState.copy(bio = bioUiState, newBio = bioUiState)
                } else {
                    uiState.copy(
                        bio = BioUiState(),
                        newBio = BioUiState()
                    )
                }.copy(isLoading = false)
            }
        }
    }
}