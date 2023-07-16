package nl.paisan.babytracker.data.mappers

import androidx.room.TypeConverter
import nl.paisan.babytracker.domain.enums.Gender

class GenderConverter {
    @TypeConverter
    fun fromGender(gender: Gender): String {
        return gender.name
    }

    @TypeConverter
    fun toGender(gender: String?): Gender? {
        return when (gender) {
            Gender.Boy.name -> Gender.Boy
            Gender.Girl.name -> Gender.Girl
            else -> null // Handle unrecognized values or null case
        }
    }
}
