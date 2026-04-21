package com.example.mealpreparationapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun MealEntityDetailsBlock(meal: MealEntity) {
    Row {
        MealThumbnail(
            imageUrl = meal.strMealThumb,
            mealName = meal.strMeal
        )

        Spacer(modifier = androidx.compose.ui.Modifier.width(12.dp))

        Column {
            Text("\"Meal\":\"${meal.strMeal ?: "null"}\",")
            Text("\"DrinkAlternate\":\"${meal.strDrinkAlternate ?: "null"}\",")
            Text("\"Category\":\"${meal.strCategory ?: "null"}\",")
            Text("\"Area\":\"${meal.strArea ?: "null"}\",")
            Text("\"Instructions\":\"${meal.strInstructions ?: "null"}\",")
            Text("\"Tags\":\"${meal.strTags ?: "null"}\",")
            Text("\"Youtube\":\"${meal.strYoutube ?: "null"}\",")
            Text("\"Thumbnail\":\"${meal.strMealThumb ?: "null"}\",")
        }
    }
}