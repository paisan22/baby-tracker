package nl.paisan.babytracker.domain.entities

import nl.paisan.babytracker.domain.enums.Gender

data class Bio(
    val id: Int? = null,
    val name: String = "",
    val birthday: Long? = null,
    val gender: Gender? = null,
    val firstLength: Int? = null,
    val firstWeight: Int? = null,
)