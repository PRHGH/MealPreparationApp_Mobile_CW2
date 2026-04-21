package com.example.mealpreparationapp

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.mealpreparationapp.model.Meal

@Composable
fun MealDetailsBlock(meal: Meal) {
    val context = LocalContext.current

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            MealThumbnail(
                imageUrl = meal.strMealThumb,
                mealName = meal.strMeal,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color(0xFFE2E8F0))
            )

            Column(modifier = Modifier.padding(20.dp)) {
                // Title
                Text(
                    text = meal.strMeal ?: "Unknown Meal",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Tags
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    MealTag(meal.strCategory ?: "Meal")
                    MealTag(meal.strArea ?: "General")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Instructions
                Text(
                    text = "Instructions",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF475569)
                )
                Text(
                    text = meal.strInstructions ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF64748B),
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Ingredients Table
                Text(
                    text = "Ingredients",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF475569)
                )
                
                Spacer(modifier = Modifier.height(8.dp))

                val ingredients = getAllIngredients(meal)
                ingredients.forEach { (name, measure) ->
                    IngredientItem(name, measure)
                }

                // YouTube Button
                if (!meal.strYoutube.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(meal.strYoutube))
                            context.startActivity(intent)
                        },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF2D37))
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(painterResource(id = R.drawable.globe), null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("Watch on YouTube", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MealTag(text: String) {
    Surface(
        color = Color(0xFFF1F5F9),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = Color(0xFF475569)
        )
    }
}

@Composable
fun IngredientItem(name: String, measure: String) {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
        color = Color(0xFFF8FAFC),
        shape = RoundedCornerShape(6.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(name, style = MaterialTheme.typography.bodySmall, color = Color(0xFF334155))
            Text(measure, style = MaterialTheme.typography.bodySmall, color = Color(0xFF64748B), fontWeight = FontWeight.Bold)
        }
    }
}

fun getAllIngredients(meal: Meal): List<Pair<String, String>> {
    val ingredients = mutableListOf<Pair<String, String>>()
    val fields = meal::class.java.declaredFields
    for (i in 1..20) {
        try {
            val ingField = meal::class.java.getDeclaredField("strIngredient$i")
            val meaField = meal::class.java.getDeclaredField("strMeasure$i")
            ingField.isAccessible = true
            meaField.isAccessible = true
            val ing = ingField.get(meal) as? String
            val mea = meaField.get(meal) as? String
            if (!ing.isNullOrBlank()) {
                ingredients.add(ing to (mea ?: ""))
            }
        } catch (e: Exception) {}
    }
    return ingredients
}
