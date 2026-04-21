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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    onAddMealsClick: () -> Unit,
    onSearchByIngredientClick: () -> Unit,
    onSearchMealsClick: () -> Unit,
    onSearchMealsOnlineClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFCFB))
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp, vertical = 64.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // App Title
        Text(
            text = "Meal API",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF3E2723), // Deep brown/coffee color
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Text(
            text = "Fetching recipes, simplified.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF8D6E63),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        // Action Buttons with Food-Inspired Palette
        MenuButton(
            text = "Add Meals to DB",
            icon = painterResource(id = R.drawable.plus),
            backgroundColor = Color(0xFF4CAF50), // Fresh Green (Vegetables)
            onClick = onAddMealsClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        MenuButton(
            text = "Search By Ingredient",
            icon = painterResource(id = R.drawable.search),
            backgroundColor = Color(0xFFFF9800), // Carrot Orange (Cooking)
            onClick = onSearchByIngredientClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        MenuButton(
            text = "Browse Saved Meals",
            icon = painterResource(id = R.drawable.database),
            backgroundColor = Color(0xFFE53935), // Tomato Red (Hearty meals)
            onClick = onSearchMealsClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        MenuButton(
            text = "Find Meals Online",
            icon = painterResource(id = R.drawable.globe),
            backgroundColor = Color(0xFF0288D1), // Sky Blue (Cloud/Global)
            onClick = onSearchMealsOnlineClick
        )
    }
}

@Composable
fun MenuButton(
    text: String,
    icon: Painter,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    ElevatedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 420.dp)
            .height(96.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = backgroundColor,
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(horizontal = 20.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 1.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Container with soft background
            Surface(
                modifier = Modifier.size(54.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color.White.copy(alpha = 0.25f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        painter = icon,
                        contentDescription = null,
                        modifier = Modifier.size(26.dp),
                        tint = Color.White
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(20.dp))
            
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    letterSpacing = 0.5.sp
                )
            )
        }
    }
}
