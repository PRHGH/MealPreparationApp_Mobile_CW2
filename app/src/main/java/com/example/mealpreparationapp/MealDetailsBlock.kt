package com.example.mealpreparationapp


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.mealpreparationapp.model.Meal

@Composable
fun MealDetailsBlock(meal: Meal) {
    Text("\"Meal\":\"${meal.strMeal ?: "null"}\",")
    Text("\"DrinkAlternate\":\"${meal.strDrinkAlternate ?: "null"}\",")
    Text("\"Category\":\"${meal.strCategory ?: "null"}\",")
    Text("\"Area\":\"${meal.strArea ?: "null"}\",")
    Text("\"Instructions\":\"${meal.strInstructions ?: "null"}\",")
    Text("\"Tags\":\"${meal.strTags ?: "null"}\",")
    Text("\"Youtube\":\"${meal.strYoutube ?: "null"}\",")

    Text("\"Ingredient1\":\"${meal.strIngredient1 ?: ""}\",")
    Text("\"Ingredient2\":\"${meal.strIngredient2 ?: ""}\",")
    Text("\"Ingredient3\":\"${meal.strIngredient3 ?: ""}\",")
    Text("\"Ingredient4\":\"${meal.strIngredient4 ?: ""}\",")
    Text("\"Ingredient5\":\"${meal.strIngredient5 ?: ""}\",")
    Text("\"Ingredient6\":\"${meal.strIngredient6 ?: ""}\",")
    Text("\"Ingredient7\":\"${meal.strIngredient7 ?: ""}\",")
    Text("\"Ingredient8\":\"${meal.strIngredient8 ?: ""}\",")
    Text("\"Ingredient9\":\"${meal.strIngredient9 ?: ""}\",")
    Text("\"Ingredient10\":\"${meal.strIngredient10 ?: ""}\",")
    Text("\"Ingredient11\":\"${meal.strIngredient11 ?: ""}\",")
    Text("\"Ingredient12\":\"${meal.strIngredient12 ?: ""}\",")
    Text("\"Ingredient13\":\"${meal.strIngredient13 ?: ""}\",")
    Text("\"Ingredient14\":\"${meal.strIngredient14 ?: ""}\",")
    Text("\"Ingredient15\":\"${meal.strIngredient15 ?: ""}\",")
    Text("\"Ingredient16\":\"${meal.strIngredient16 ?: ""}\",")
    Text("\"Ingredient17\":\"${meal.strIngredient17 ?: ""}\",")
    Text("\"Ingredient18\":\"${meal.strIngredient18 ?: ""}\",")
    Text("\"Ingredient19\":\"${meal.strIngredient19 ?: ""}\",")
    Text("\"Ingredient20\":\"${meal.strIngredient20 ?: ""}\",")

    Text("\"Measure1\":\"${meal.strMeasure1 ?: ""}\",")
    Text("\"Measure2\":\"${meal.strMeasure2 ?: ""}\",")
    Text("\"Measure3\":\"${meal.strMeasure3 ?: ""}\",")
    Text("\"Measure4\":\"${meal.strMeasure4 ?: ""}\",")
    Text("\"Measure5\":\"${meal.strMeasure5 ?: ""}\",")
    Text("\"Measure6\":\"${meal.strMeasure6 ?: ""}\",")
    Text("\"Measure7\":\"${meal.strMeasure7 ?: ""}\",")
    Text("\"Measure8\":\"${meal.strMeasure8 ?: ""}\",")
    Text("\"Measure9\":\"${meal.strMeasure9 ?: ""}\",")
    Text("\"Measure10\":\"${meal.strMeasure10 ?: ""}\",")
    Text("\"Measure11\":\"${meal.strMeasure11 ?: ""}\",")
    Text("\"Measure12\":\"${meal.strMeasure12 ?: ""}\",")
    Text("\"Measure13\":\"${meal.strMeasure13 ?: ""}\",")
    Text("\"Measure14\":\"${meal.strMeasure14 ?: ""}\",")
    Text("\"Measure15\":\"${meal.strMeasure15 ?: ""}\",")
    Text("\"Measure16\":\"${meal.strMeasure16 ?: ""}\",")
    Text("\"Measure17\":\"${meal.strMeasure17 ?: ""}\",")
    Text("\"Measure18\":\"${meal.strMeasure18 ?: ""}\",")
    Text("\"Measure19\":\"${meal.strMeasure19 ?: ""}\",")
    Text("\"Measure20\":\"${meal.strMeasure20 ?: ""}\"")
}