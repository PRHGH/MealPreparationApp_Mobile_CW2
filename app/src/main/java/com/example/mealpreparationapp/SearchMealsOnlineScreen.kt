package com.example.mealpreparationapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
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
fun SearchMealsOnlineScreen(
    onBackClick: () -> Unit
) {
    var query by rememberSaveable { mutableStateOf("") }
    var statusMessage by rememberSaveable { mutableStateOf("Enter part of a meal name") }
    var results by rememberSaveable(stateSaver = MealListSaver) {
        mutableStateOf(emptyList<Meal>())
    }
    val scope = rememberCoroutineScope()
    val themeColor = Color(0xFF0288D1) // Sky Blue matching "Find Meals Online" button

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFCFB))
            .padding(horizontal = 20.dp)
    ) {
        // Header with Back Button
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
                text = "Online Search",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF3E2723),
                modifier = Modifier.padding(start = 12.dp)
            )
        }

        // Search Input
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("Search by name (e.g. Teriyaki, Pie...)") },
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
                Icon(painterResource(id = R.drawable.globe), null, modifier = Modifier.size(20.dp))
            }
        )

        // Search Button
        ElevatedButton(
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
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = themeColor,
                contentColor = Color.White
            )
        ) {
            Icon(painterResource(id = R.drawable.search), null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text("Find Meals", fontWeight = FontWeight.Bold)
        }

        // Status Message
        Text(
            text = statusMessage,
            style = MaterialTheme.typography.labelMedium,
            color = Color(0xFF8D6E63),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        )

        // Results List
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            for (meal in results) {
                MealDetailsBlock(meal)
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
