package nl.paisan.babytracker.domain.commands

import nl.paisan.babytracker.domain.enums.BottleType

data class AddBottleLogCommand (
    val start: Long,
    val end: Long,
    val bottleType: BottleType,
    val millimeters: Int
)