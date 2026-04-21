package com.example.mealpreparationapp

import com.example.mealpreparationapp.model.Meal

object MealApiService {

    suspend fun searchMealsByIngredient(ingredient: String): List<Meal> {
        val cleanIngredient = ingredient.trim()
        if (cleanIngredient.isEmpty()) return emptyList()

        val encodedIngredient = NetworkUtils.encode(cleanIngredient)

        val filterUrl =
            "https://www.themealdb.com/api/json/v1/1/filter.php?i=$encodedIngredient"

        val filterResponse = NetworkUtils.getUrlContent(filterUrl)
        val mealIds = JsonParser.parseMealIdsFromIngredientFilter(filterResponse)

        val fullMeals = mutableListOf<Meal>()

        for (id in mealIds) {
            val lookupUrl =
                "https://www.themealdb.com/api/json/v1/1/lookup.php?i=$id"

            val lookupResponse = NetworkUtils.getUrlContent(lookupUrl)
            val meal = JsonParser.parseSingleMeal(lookupResponse)

            if (meal != null) {
                fullMeals.add(meal)
            }
        }

        return fullMeals
    }

    suspend fun searchMealsByName(query: String): List<Meal> {
        val cleanQuery = query.trim()
        if (cleanQuery.isEmpty()) return emptyList()

        val encodedQuery = NetworkUtils.encode(cleanQuery)

        val searchUrl =
            "https://www.themealdb.com/api/json/v1/1/search.php?s=$encodedQuery"

        val response = NetworkUtils.getUrlContent(searchUrl)
        return JsonParser.parseMealsList(response)
    }
}