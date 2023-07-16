package nl.paisan.babytracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import nl.paisan.babytracker.domain.enums.Gender

@Entity(tableName = "bio")
data class Bio(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String = "",
    val birthday: Long? = null,
    val gender: Gender? = null,
    val firstLength: Int? = null,
    val firstWeight: Int? = null
)
