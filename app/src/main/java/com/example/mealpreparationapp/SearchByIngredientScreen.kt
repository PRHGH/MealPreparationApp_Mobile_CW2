package com.example.mealpreparationapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mealpreparationapp.model.Meal
import kotlinx.coroutines.launch

@Composable
fun SearchByIngredientScreen(
    mealDao: MealDao,
    onBackClick: () -> Unit
) {
    // rememberSaveable = rotation safe
    var ingredient by rememberSaveable { mutableStateOf("") }
    var statusMessage by rememberSaveable { mutableStateOf("Ready to search") }
    // Preserve list on rotation
    var retrievedMeals by rememberSaveable(stateSaver = MealListSaver) {
        mutableStateOf(emptyList<Meal>())
    }
    // Coroutine for async
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFCFB))
            .padding(horizontal = 20.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                onClick = onBackClick,
                modifier = Modifier.size(40.dp),
                shape = RoundedCornerShape(10.dp),
                color = Color.Transparent
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "Back",
                        modifier = Modifier.size(20.dp),
                        tint = Color(0xFF3E2723)
                    )
                }
            }
            Text(
                text = "Ingredient Search",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF3E2723),
                modifier = Modifier.padding(start = 12.dp)
            )
        }

        OutlinedTextField(
            value = ingredient,
            onValueChange = { ingredient = it },
            placeholder = { Text("e.g. Chicken, Pasta...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedBorderColor = Color(0xFFE2E8F0)
            ),
            leadingIcon = {
                Icon(painterResource(id = R.drawable.search), null, modifier = Modifier.size(20.dp))
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Retrieve Button
            ElevatedButton(
                onClick = {
                    // Avoid UI blocking
                    scope.launch {
                        try {
                            val cleanIngredient = ingredient.trim()
                            if (cleanIngredient.isEmpty()) {
                                statusMessage = "Please enter an ingredient"
                                retrievedMeals = emptyList()
                            } else {
                                statusMessage = "Searching..."
                                // API: ingredient -> IDs -> details
                                val meals = MealApiService.searchMealsByIngredient(cleanIngredient)
                                retrievedMeals = meals
                                statusMessage = if (meals.isEmpty()) "No meals found" else "${meals.size} meals found"
                            }
                        } catch (e: Exception) {
                            retrievedMeals = emptyList()
                            statusMessage = "Network error"
                        }
                    }
                },
                modifier = Modifier.weight(1f).height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color(0xFFFF9800),
                    contentColor = Color.White
                )
            ) {
                Icon(painterResource(id = R.drawable.search), null, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(8.dp))
                Text("Retrieve", fontWeight = FontWeight.Bold)
            }

            // Save Button
            ElevatedButton(
                onClick = {
                    // Avoid UI blocking
                    scope.launch {
                        try {
                            if (retrievedMeals.isEmpty()) {
                                statusMessage = "Nothing to save"
                            } else {
                                val entities = retrievedMeals.map { it.toEntity() }
                                // Room DB insert
                                mealDao.insertMeals(entities)
                                statusMessage = "Saved to DB!"
                            }
                        } catch (e: Exception) {
                            statusMessage = "Save failed"
                        }
                    }
                },
                modifier = Modifier.weight(1f).height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color(0xFFF44336),
                    contentColor = Color.White
                )
            ) {
                Icon(painterResource(id = R.drawable.database), null, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(8.dp))
                Text("Save to DB", fontWeight = FontWeight.Bold)
            }
        }

        Text(
            text = statusMessage,
            style = MaterialTheme.typography.labelMedium,
            color = Color(0xFF8D6E63),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        )

        // Display list
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            for (meal in retrievedMeals) {
                MealDetailsBlock(meal)
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
