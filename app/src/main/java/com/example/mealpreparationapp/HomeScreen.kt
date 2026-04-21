package com.example.mealpreparationapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onAddMealsClick: () -> Unit,
    onSearchByIngredientClick: () -> Unit,
    onSearchMealsClick: () -> Unit,
    onSearchMealsOnlineClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onAddMealsClick) {
            Text("Add Meals to DB")
        }

        Button(onClick = onSearchByIngredientClick) {
            Text("Search for Meals By Ingredient")
        }

        Button(onClick = onSearchMealsClick) {
            Text("Search for Meals")
        }

        Button(onClick = onSearchMealsOnlineClick) {
            Text("Search Meals Online")
        }
    }
}