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
fun SearchMealsOnlineScreen(
    onBackClick: () -> Unit
) {
    var query by rememberSaveable { mutableStateOf("") }
    var statusMessage by rememberSaveable { mutableStateOf("Enter part of a meal name") }
    var results by rememberSaveable(stateSaver = MealListSaver) {
        mutableStateOf(emptyList<Meal>())
    }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Search Meals Online")

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Meal name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                scope.launch {
                    try {
                        val cleanQuery = query.trim()

                        if (cleanQuery.isEmpty()) {
                            results = emptyList()
                            statusMessage = "Please enter some text"
                        } else {
                            statusMessage = "Searching online..."
                            val foundMeals = MealApiService.searchMealsByName(cleanQuery)

                            results = foundMeals

                            statusMessage = if (foundMeals.isEmpty()) {
                                "No meals found"
                            } else {
                                "${foundMeals.size} meals found online"
                            }
                        }
                    } catch (e: Exception) {
                        results = emptyList()
                        statusMessage = "Error searching meals online"
                    }
                }
            }
        ) {
            Text("Search")
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
            for (meal in results) {
                MealDetailsBlock(meal)
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        Button(onClick = onBackClick) {
            Text("Back")
        }
    }
}