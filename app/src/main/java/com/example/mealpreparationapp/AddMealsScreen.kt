package com.example.mealpreparationapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun AddMealsScreen(
    mealDao: MealDao,
    onBackClick: () -> Unit
) {
    var statusMessage by rememberSaveable { mutableStateOf("Ready to populate database") }
    val scope = rememberCoroutineScope()
    val themeColor = Color(0xFF4CAF50) // Matching "Add Meals" button color

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFCFB))
            .padding(24.dp)
    ) {
        
        Surface(
            onClick = onBackClick,
            modifier = Modifier
                .padding(top = 40.dp)
                .size(48.dp),
            shape = RoundedCornerShape(12.dp),
            color = Color.Transparent
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp),
                    tint = Color(0xFF3E2723)
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Hero Icon Container
            Surface(
                modifier = Modifier.size(120.dp),
                shape = RoundedCornerShape(32.dp),
                color = themeColor.copy(alpha = 0.1f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        painter = painterResource(id = R.drawable.database),
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = themeColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Local Database",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF3E2723)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Add pre-defined meal data to collection.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF8D6E63),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Main Action Button (Matching Home Screen style)
            ElevatedButton(
                onClick = {
                    scope.launch {
                        val mealsToInsert = MealSeedData.meals
                        if (mealsToInsert.isEmpty()) {
                            statusMessage = "No meals found to insert."
                        } else {
                            mealDao.insertMeals(mealsToInsert)
                            statusMessage = "Success! ${mealsToInsert.size} meals added."
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 420.dp)
                    .height(96.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = themeColor,
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.size(54.dp),
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White.copy(alpha = 0.25f)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                painter = painterResource(id = R.drawable.plus),
                                contentDescription = null,
                                modifier = Modifier.size(26.dp),
                                tint = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    Text(
                        "Add Meals to DB",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Status Message Box
            Surface(
                color = if (statusMessage.contains("Success")) 
                    Color(0xFFE8F5E9) else Color(0xFFF5F5F5),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.widthIn(max = 300.dp)
            ) {
                Text(
                    text = statusMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (statusMessage.contains("Success")) 
                        Color(0xFF2E7D32) else Color(0xFF8D6E63),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
                )
            }
        }
    }
}
