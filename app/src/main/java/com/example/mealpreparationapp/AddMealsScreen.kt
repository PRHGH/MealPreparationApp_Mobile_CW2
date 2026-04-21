package com.example.mealpreparationapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun AddMealsScreen(
    mealDao: MealDao,
    onBackClick: () -> Unit
) {
    var statusMessage by rememberSaveable { mutableStateOf("No action yet") }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Add Meals to DB")

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                scope.launch {
                    val mealsToInsert = MealSeedData.meals

                    if (mealsToInsert.isEmpty()) {
                        statusMessage = "No meals available to insert"
                    } else {
                        mealDao.insertMeals(mealsToInsert)
                        statusMessage = "${mealsToInsert.size} meals inserted successfully"
                    }
                }
            }
        ) {
            Text("Insert Hardcoded Meals")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(statusMessage)

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onBackClick) {
            Text("Back")
        }
    }
}