package com.example.mealpreparationapp


import org.json.JSONArray
import org.json.JSONObject
import com.example.mealpreparationapp.model.Meal

object JsonParser {

    // Extract Meal IDs
    fun parseMealIdsFromIngredientFilter(jsonText: String): List<String> {
        val result = mutableListOf<String>()
        val root = JSONObject(jsonText)

        if (root.isNull("meals")) return result

        val mealsArray = root.getJSONArray("meals")
        for (i in 0 until mealsArray.length()) {
            val item = mealsArray.getJSONObject(i)
            result.add(item.getString("idMeal"))
        }

        return result
    }

    // Parse one meal
    fun parseSingleMeal(jsonText: String): Meal? {
        val root = JSONObject(jsonText)

        if (root.isNull("meals")) return null

        val mealsArray: JSONArray = root.getJSONArray("meals")
        if (mealsArray.length() == 0) return null

        val mealObject = mealsArray.getJSONObject(0)

        return Meal(
            idMeal = mealObject.optString("idMeal", ""),
            strMeal = mealObject.optNullableString("strMeal"),
            strDrinkAlternate = mealObject.optNullableString("strDrinkAlternate"),
            strCategory = mealObject.optNullableString("strCategory"),
            strArea = mealObject.optNullableString("strArea"),
            strInstructions = mealObject.optNullableString("strInstructions"),
            strTags = mealObject.optNullableString("strTags"),
            strYoutube = mealObject.optNullableString("strYoutube"),
            strMealThumb = mealObject.optNullableString("strMealThumb"),

            strIngredient1 = mealObject.optNullableString("strIngredient1"),
            strIngredient2 = mealObject.optNullableString("strIngredient2"),
            strIngredient3 = mealObject.optNullableString("strIngredient3"),
            strIngredient4 = mealObject.optNullableString("strIngredient4"),
            strIngredient5 = mealObject.optNullableString("strIngredient5"),
            strIngredient6 = mealObject.optNullableString("strIngredient6"),
            strIngredient7 = mealObject.optNullableString("strIngredient7"),
            strIngredient8 = mealObject.optNullableString("strIngredient8"),
            strIngredient9 = mealObject.optNullableString("strIngredient9"),
            strIngredient10 = mealObject.optNullableString("strIngredient10"),
            strIngredient11 = mealObject.optNullableString("strIngredient11"),
            strIngredient12 = mealObject.optNullableString("strIngredient12"),
            strIngredient13 = mealObject.optNullableString("strIngredient13"),
            strIngredient14 = mealObject.optNullableString("strIngredient14"),
            strIngredient15 = mealObject.optNullableString("strIngredient15"),
            strIngredient16 = mealObject.optNullableString("strIngredient16"),
            strIngredient17 = mealObject.optNullableString("strIngredient17"),
            strIngredient18 = mealObject.optNullableString("strIngredient18"),
            strIngredient19 = mealObject.optNullableString("strIngredient19"),
            strIngredient20 = mealObject.optNullableString("strIngredient20"),

            strMeasure1 = mealObject.optNullableString("strMeasure1"),
            strMeasure2 = mealObject.optNullableString("strMeasure2"),
            strMeasure3 = mealObject.optNullableString("strMeasure3"),
            strMeasure4 = mealObject.optNullableString("strMeasure4"),
            strMeasure5 = mealObject.optNullableString("strMeasure5"),
            strMeasure6 = mealObject.optNullableString("strMeasure6"),
            strMeasure7 = mealObject.optNullableString("strMeasure7"),
            strMeasure8 = mealObject.optNullableString("strMeasure8"),
            strMeasure9 = mealObject.optNullableString("strMeasure9"),
            strMeasure10 = mealObject.optNullableString("strMeasure10"),
            strMeasure11 = mealObject.optNullableString("strMeasure11"),
            strMeasure12 = mealObject.optNullableString("strMeasure12"),
            strMeasure13 = mealObject.optNullableString("strMeasure13"),
            strMeasure14 = mealObject.optNullableString("strMeasure14"),
            strMeasure15 = mealObject.optNullableString("strMeasure15"),
            strMeasure16 = mealObject.optNullableString("strMeasure16"),
            strMeasure17 = mealObject.optNullableString("strMeasure17"),
            strMeasure18 = mealObject.optNullableString("strMeasure18"),
            strMeasure19 = mealObject.optNullableString("strMeasure19"),
            strMeasure20 = mealObject.optNullableString("strMeasure20")
        )
    }

    // Parse list
    fun parseMealsList(jsonText: String): List<Meal> {
        val result = mutableListOf<Meal>()
        val root = JSONObject(jsonText)

        if (root.isNull("meals")) return result

        val mealsArray = root.getJSONArray("meals")

        for (i in 0 until mealsArray.length()) {
            val mealObject = mealsArray.getJSONObject(i)

            val meal = Meal(
                idMeal = mealObject.optString("idMeal", ""),
                strMeal = mealObject.optNullableString("strMeal"),
                strDrinkAlternate = mealObject.optNullableString("strDrinkAlternate"),
                strCategory = mealObject.optNullableString("strCategory"),
                strArea = mealObject.optNullableString("strArea"),
                strInstructions = mealObject.optNullableString("strInstructions"),
                strTags = mealObject.optNullableString("strTags"),
                strYoutube = mealObject.optNullableString("strYoutube"),
                strMealThumb = mealObject.optNullableString("strMealThumb"),

                strIngredient1 = mealObject.optNullableString("strIngredient1"),
                strIngredient2 = mealObject.optNullableString("strIngredient2"),
                strIngredient3 = mealObject.optNullableString("strIngredient3"),
                strIngredient4 = mealObject.optNullableString("strIngredient4"),
                strIngredient5 = mealObject.optNullableString("strIngredient5"),
                strIngredient6 = mealObject.optNullableString("strIngredient6"),
                strIngredient7 = mealObject.optNullableString("strIngredient7"),
                strIngredient8 = mealObject.optNullableString("strIngredient8"),
                strIngredient9 = mealObject.optNullableString("strIngredient9"),
                strIngredient10 = mealObject.optNullableString("strIngredient10"),
                strIngredient11 = mealObject.optNullableString("strIngredient11"),
                strIngredient12 = mealObject.optNullableString("strIngredient12"),
                strIngredient13 = mealObject.optNullableString("strIngredient13"),
                strIngredient14 = mealObject.optNullableString("strIngredient14"),
                strIngredient15 = mealObject.optNullableString("strIngredient15"),
                strIngredient16 = mealObject.optNullableString("strIngredient16"),
                strIngredient17 = mealObject.optNullableString("strIngredient17"),
                strIngredient18 = mealObject.optNullableString("strIngredient18"),
                strIngredient19 = mealObject.optNullableString("strIngredient19"),
                strIngredient20 = mealObject.optNullableString("strIngredient20"),

                strMeasure1 = mealObject.optNullableString("strMeasure1"),
                strMeasure2 = mealObject.optNullableString("strMeasure2"),
                strMeasure3 = mealObject.optNullableString("strMeasure3"),
                strMeasure4 = mealObject.optNullableString("strMeasure4"),
                strMeasure5 = mealObject.optNullableString("strMeasure5"),
                strMeasure6 = mealObject.optNullableString("strMeasure6"),
                strMeasure7 = mealObject.optNullableString("strMeasure7"),
                strMeasure8 = mealObject.optNullableString("strMeasure8"),
                strMeasure9 = mealObject.optNullableString("strMeasure9"),
                strMeasure10 = mealObject.optNullableString("strMeasure10"),
                strMeasure11 = mealObject.optNullableString("strMeasure11"),
                strMeasure12 = mealObject.optNullableString("strMeasure12"),
                strMeasure13 = mealObject.optNullableString("strMeasure13"),
                strMeasure14 = mealObject.optNullableString("strMeasure14"),
                strMeasure15 = mealObject.optNullableString("strMeasure15"),
                strMeasure16 = mealObject.optNullableString("strMeasure16"),
                strMeasure17 = mealObject.optNullableString("strMeasure17"),
                strMeasure18 = mealObject.optNullableString("strMeasure18"),
                strMeasure19 = mealObject.optNullableString("strMeasure19"),
                strMeasure20 = mealObject.optNullableString("strMeasure20")
            )

            result.add(meal)
        }

        return result
    }

    private fun JSONObject.optNullableString(key: String): String? {
        if (!has(key) || isNull(key)) return null
        val value = optString(key, "")
        return if (value.isBlank()) null else value
    }
}
