package nl.paisan.babytracker.data.mappers

import nl.paisan.babytracker.data.entities.Bio

fun Bio.toDomain(): nl.paisan.babytracker.domain.entities.Bio {
    return nl.paisan.babytracker.domain.entities.Bio(
        id = this.id,
        name = this.name,
        birthday = this.birthday,
        gender = this.gender,
        firstLength = this.firstLength,
        firstWeight = this.firstWeight
    )
}

fun List<Bio>.toDomain(): List<nl.paisan.babytracker.domain.entities.Bio> {
    return this.map { it.toDomain() }
}

fun nl.paisan.babytracker.domain.entities.Bio.toDBModel(): Bio {
    return Bio(
        id = this.id ?: 0,
        name = this.name,
        birthday = this.birthday,
        gender = this.gender,
        firstLength = this.firstLength,
        firstWeight = this.firstWeight
    )
}