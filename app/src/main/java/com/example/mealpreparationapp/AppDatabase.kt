package com.example.mealpreparationapp

// Room = SQLite abstraction
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MealEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    // DB access point
    abstract fun mealDao(): MealDao
}
