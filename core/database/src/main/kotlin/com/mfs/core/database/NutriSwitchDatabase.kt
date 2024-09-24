package com.mfs.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        FoodDao::class
    ],
    version = 1,
    exportSchema = false
)
abstract class NutriSwitchDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDao

    companion object {
        fun build(applicationContext: Context): NutriSwitchDatabase {
            return Room.databaseBuilder(
                applicationContext,
                NutriSwitchDatabase::class.java,
                "nutri_switch_db"
            ).createFromAsset("database/nutri_switch_database.db")
                .build()
        }
    }
}