package com.example.mealpreparationapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
fun SearchMealsScreen(
    mealDao: MealDao,
    onBackClick: () -> Unit
) {
    var query by rememberSaveable { mutableStateOf("") }
    var statusMessage by rememberSaveable { mutableStateOf("Enter text to search") }
    var results by rememberSaveable(stateSaver = MealListSaver) {
        mutableStateOf(emptyList<Meal>())
    }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Search for Meals")

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Meal name or ingredient") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                scope.launch {
                    val cleanQuery = query.trim()

                    if (cleanQuery.isEmpty()) {
                        results = emptyList()
                        statusMessage = "Please enter some text"
                    } else {
                        val foundMeals = mealDao.searchMeals(cleanQuery)
                        val uiMeals = foundMeals.map { it.toMeal() }

                        results = uiMeals

                        statusMessage = if (uiMeals.isEmpty()) {
                            "No meals found"
                        } else {
                            "${uiMeals.size} meals found"
                        }
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
                MealWithThumbnailBlock(meal)
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        Button(onClick = onBackClick) {
            Text("Back")
        }
    }
}

@Composable
fun MealWithThumbnailBlock(meal: Meal) {
    Row {
        MealThumbnail(
            imageUrl = meal.strMealThumb,
            mealName = meal.strMeal
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            MealDetailsBlock(meal)
        }
    }
}