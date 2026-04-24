package com.example.mealpreparationapp

// Network logic
import com.example.mealpreparationapp.model.Meal

object MealApiService {

    // API: ingredient -> IDs -> details
    suspend fun searchMealsByIngredient(ingredient: String): List<Meal> {
        val cleanIngredient = ingredient.trim()
        if (cleanIngredient.isEmpty()) return emptyList()

        val encodedIngredient = NetworkUtils.encode(cleanIngredient)

        // API Filter endpoint
        val filterUrl =
            "https://www.themealdb.com/api/json/v1/1/filter.php?i=$encodedIngredient"

        val filterResponse = NetworkUtils.getUrlContent(filterUrl)
        val mealIds = JsonParser.parseMealIdsFromIngredientFilter(filterResponse)

        val fullMeals = mutableListOf<Meal>()

        // Lookup each ID for full details
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

    // API search by name
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
