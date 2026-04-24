package com.example.mealpreparationapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MealDao {

    // Room DB insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeals(meals: List<MealEntity>)

    // Room DB insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: MealEntity)

    // Retrieve all
    @Query("SELECT * FROM meals")
    suspend fun getAllMeals(): List<MealEntity>

    // Local DB search by name or ingredient
    @Query("""
    SELECT * FROM meals
    WHERE 
        LOWER(strMeal) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(IFNULL(strIngredient1, '')) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(IFNULL(strIngredient2, '')) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(IFNULL(strIngredient3, '')) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(IFNULL(strIngredient4, '')) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(IFNULL(strIngredient5, '')) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(IFNULL(strIngredient6, '')) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(IFNULL(strIngredient7, '')) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(IFNULL(strIngredient8, '')) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(IFNULL(strIngredient9, '')) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(IFNULL(strIngredient10, '')) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(IFNULL(strIngredient11, '')) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(IFNULL(strIngredient12, '')) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(IFNULL(strIngredient13, '')) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(IFNULL(strIngredient14, '')) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(IFNULL(strIngredient15, '')) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(IFNULL(strIngredient16, '')) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(IFNULL(strIngredient17, '')) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(IFNULL(strIngredient18, '')) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(IFNULL(strIngredient19, '')) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(IFNULL(strIngredient20, '')) LIKE '%' || LOWER(:query) || '%'
""")
    suspend fun searchMeals(query: String): List<MealEntity>
}
