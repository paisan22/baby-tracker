package nl.paisan.babytracker.ui.screen.bio

import nl.paisan.babytracker.domain.enums.Gender

data class BioScreenUiState (
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val bio: BioUiState = BioUiState(),
    val newBio: BioUiState = BioUiState(),

    val hasUpdates: Boolean = false,
)

data class BioUiState(
    val id: Int? = 0,
    val name: String = "",
    val birthday: Long? = null,
    val gender: Gender? = null,
    val weight: Int? = null,
    val length: Int? = null,
)