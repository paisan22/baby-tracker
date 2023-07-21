package nl.paisan.babytracker.data.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Example migration
 */
class MigrationFrom1To2 : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Create a temporary table with the new schema
        database.execSQL("CREATE TABLE bio_new (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "name TEXT NOT NULL, " +
                "birthday INTEGER, " +
                "gender TEXT, " +
                "firstLength REAL, " +
                "firstWeight REAL" +
                ")")

        // Copy the existing data to the temporary table
        database.execSQL("INSERT INTO bio_new (id, name) SELECT id, name FROM bio")

        // Remove the old table
        database.execSQL("DROP TABLE bio")

        // Rename the temporary table to the original table name
        database.execSQL("ALTER TABLE bio_new RENAME TO bio")
    }
}