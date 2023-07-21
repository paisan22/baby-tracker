package nl.paisan.babytracker.domain.commands

import nl.paisan.babytracker.domain.enums.BreastSide

data class AddBreastLogCommand (
    val start: Long,
    val end: Long,
    val breastSide: BreastSide
)