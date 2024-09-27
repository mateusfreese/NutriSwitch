package com.mfs.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [
        FoodEntity::class, FoodEntityFts::class
    ],
    version = 1,
    exportSchema = false
)
abstract class NutriSwitchDatabase : RoomDatabase() {

    abstract fun getFoodDao(): FoodDao

    companion object {
        fun build(applicationContext: Context): NutriSwitchDatabase {
            return Room.databaseBuilder(
                applicationContext,
                NutriSwitchDatabase::class.java,
                "nutri_switch_db"
            ).createFromAsset("database/nutri_switch_database.db")
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        db.execSQL("INSERT INTO food_fts(food_fts) VALUES ('rebuild')")
                    }
                })
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}