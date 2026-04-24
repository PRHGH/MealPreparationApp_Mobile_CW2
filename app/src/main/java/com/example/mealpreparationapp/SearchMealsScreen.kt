package com.example.mealpreparationapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mealpreparationapp.model.Meal
import kotlinx.coroutines.launch

// Database Search Screen
@Composable
fun SearchMealsScreen(
    mealDao: MealDao,
    onBackClick: () -> Unit
) {
    // Keeps data safe during screen rotation
    var query by rememberSaveable { mutableStateOf("") }
    var statusMessage by rememberSaveable { mutableStateOf("Ready to browse database") }
    var results by rememberSaveable(stateSaver = MealListSaver) {
        mutableStateOf(emptyList<Meal>())
    }
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
                text = "Browse Meals",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF3E2723),
                modifier = Modifier.padding(start = 12.dp)
            )
        }

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("Search saved meals...") },
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

        ElevatedButton(
            onClick = {
                // scope.launch: Runs DB query off-main-thread to avoid UI freezing
                scope.launch {
                    val cleanQuery = query.trim()
                    statusMessage = "Searching database..."
                    val foundMeals = mealDao.searchMeals(cleanQuery)
                    val uiMeals = foundMeals.map { it.toMeal() }
                    results = uiMeals
                    statusMessage = if (uiMeals.isEmpty()) "No meals found" else "${uiMeals.size} meals found"
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Color(0xFFE53935),
                contentColor = Color.White
            )
        ) {
            Icon(painterResource(id = R.drawable.search), null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text("Search Collection", fontWeight = FontWeight.Bold)
        }

        Text(
            text = statusMessage,
            style = MaterialTheme.typography.labelMedium,
            color = Color(0xFF8D6E63),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        )

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
