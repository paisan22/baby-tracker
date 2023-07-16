package nl.paisan.babytracker.data.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class MigrationFrom2To3 : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Create a new table with the updated schema
        database.execSQL(
            "CREATE TABLE bio_new (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "name TEXT NOT NULL, " +
                    "birthday INTEGER, " +
                    "gender TEXT, " +
                    "firstLength INTEGER, " +
                    "firstWeight INTEGER)"
        )

        // Copy the data from the old table to the new table, converting the Double values to Int
        database.execSQL(
            "INSERT INTO bio_new (id, name, birthday, gender, firstLength, firstWeight) " +
                    "SELECT id, name, birthday, gender, CAST(firstLength AS INTEGER), " +
                    "CAST(firstWeight AS INTEGER) FROM bio"
        )

        // Remove the old table
        database.execSQL("DROP TABLE bio")

        // Rename the new table to the original table name
        database.execSQL("ALTER TABLE bio_new RENAME TO bio")
    }
}
