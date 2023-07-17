package nl.paisan.babytracker.data.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class MigrationFrom3To4 : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `nutrition_log` " +
                "(`id` INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "`startTime` INTEGER NOT NULL, " +
                "`endTime` INTEGER NOT NULL, " +
                "`breastLogId` INTEGER, " +
                "`bottleLogId` INTEGER)")

        database.execSQL("CREATE TABLE IF NOT EXISTS `breast_log` " +
                "(`id` INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "`breastSide` TEXT NOT NULL)")

        database.execSQL("CREATE TABLE IF NOT EXISTS `bottle_log` " +
                "(`id` INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "`bottleType` TEXT NOT NULL, " +
                "`milliliters` INTEGER NOT NULL)")

        // Transfer data from old bio table to new nutrition_log table
        database.execSQL("INSERT INTO `nutrition_log` " +
                "(`startTime`, `endTime`) " +
                "SELECT `startTime`, `endTime` FROM `bio` LIMIT 1")

        // Drop old bio table
        database.execSQL("DROP TABLE IF EXISTS `bio`")
    }
}