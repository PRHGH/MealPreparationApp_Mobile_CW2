package com.example.mealpreparationapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mealpreparationapp.model.Meal
import kotlinx.coroutines.launch

@Composable
fun SearchByIngredientScreen(
    mealDao: MealDao,
    onBackClick: () -> Unit
) {
    var ingredient by rememberSaveable { mutableStateOf("") }
    var statusMessage by rememberSaveable { mutableStateOf("Enter an ingredient") }
    var retrievedMeals by rememberSaveable(stateSaver = MealListSaver) {
        mutableStateOf(emptyList<Meal>())
    }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Search for Meals By Ingredient")

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = ingredient,
            onValueChange = { ingredient = it },
            label = { Text("Ingredient") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                scope.launch {
                    try {
                        val cleanIngredient = ingredient.trim()

                        if (cleanIngredient.isEmpty()) {
                            statusMessage = "Please enter an ingredient"
                            retrievedMeals = emptyList()
                        } else {
                            statusMessage = "Retrieving meals..."
                            val meals = MealApiService.searchMealsByIngredient(cleanIngredient)

                            retrievedMeals = meals

                            statusMessage = if (meals.isEmpty()) {
                                "No meals found"
                            } else {
                                "${meals.size} meals retrieved"
                            }
                        }
                    } catch (e: Exception) {
                        retrievedMeals = emptyList()
                        statusMessage = "Error retrieving meals"
                    }
                }
            }
        ) {
            Text("Retrieve Meals")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                scope.launch {
                    try {
                        if (retrievedMeals.isEmpty()) {
                            statusMessage = "No retrieved meals to save"
                        } else {
                            val entities = retrievedMeals.map { it.toEntity() }
                            mealDao.insertMeals(entities)
                            statusMessage = "${entities.size} meals saved to database"
                        }
                    } catch (e: Exception) {
                        statusMessage = "Error saving meals"
                    }
                }
            }
        ) {
            Text("Save meals to Database")
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text(statusMessage)
        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top
        ) {
            for (meal in retrievedMeals) {
                MealDetailsBlock(meal)
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        Button(onClick = onBackClick) {
            Text("Back")
        }
    }
}