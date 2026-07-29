package nl.paisan.babytracker.data.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Adds indices on the columns every overview screen sorts/queries by
 * (previously unindexed, causing a full table scan + sort on every read).
 */
class MigrationFrom1To2 : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE INDEX IF NOT EXISTS index_nutrition_log_startTime ON nutrition_log(startTime)")
        database.execSQL("CREATE INDEX IF NOT EXISTS index_rest_log_start ON rest_log(start)")
        database.execSQL("CREATE INDEX IF NOT EXISTS index_diaper_log_start ON diaper_log(start)")
        database.execSQL("CREATE INDEX IF NOT EXISTS index_weight_measurement_registrationDate ON weight_measurement(registrationDate)")
        database.execSQL("CREATE INDEX IF NOT EXISTS index_length_measurement_registrationDate ON length_measurement(registrationDate)")
    }
}
