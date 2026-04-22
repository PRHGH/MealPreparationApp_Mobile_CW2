package com.example.mealpreparationapp

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.mealpreparationapp.model.Meal
import org.json.JSONObject

@Composable
fun MealDetailsBlock(meal: Meal) {
    val context = LocalContext.current
    var showJsonDialog by remember { mutableStateOf(false) }

    if (showJsonDialog) {
        val jsonString = remember(meal) {
            try {
                val json = JSONObject()
                json.put("idMeal", meal.idMeal)
                json.put("strMeal", meal.strMeal)
                json.put("strCategory", meal.strCategory)
                json.put("strArea", meal.strArea)
                json.put("strInstructions", meal.strInstructions)
                json.put("strMealThumb", meal.strMealThumb)
                json.put("strYoutube", meal.strYoutube)

                // Add ingredients
                val ingredients = getAllIngredients(meal)
                ingredients.forEachIndexed { index, (name, measure) ->
                    json.put("strIngredient${index + 1}", name)
                    json.put("strMeasure${index + 1}", measure)
                }

                json.toString(4) // Indent for readability
            } catch (e: Exception) {
                "Error generating JSON"
            }
        }

        Dialog(onDismissRequest = { showJsonDialog = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Meal JSON Data",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(Color(0xFFF1F5F9), RoundedCornerShape(8.dp))
                            .padding(8.dp)
                    ) {
                        val scroll = rememberScrollState()
                        Text(
                            text = jsonString,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.verticalScroll(scroll)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { showJsonDialog = false },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Close")
                    }
                }
            }
        }
    }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box {
                MealThumbnail(
                    imageUrl = meal.strMealThumb,
                    mealName = meal.strMeal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color(0xFFE2E8F0))
                )

                // Tiny JSON button
                Surface(
                    onClick = { showJsonDialog = true },
                    modifier = Modifier
                        .padding(12.dp)
                        .size(32.dp)
                        .align(Alignment.TopEnd),
                    shape = RoundedCornerShape(8.dp),
                    color = Color.Black.copy(alpha = 0.4f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = "Show JSON",
                            modifier = Modifier.size(20.dp),
                            tint = Color.White
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = meal.strMeal ?: "Unknown Meal",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    MealTag(meal.strCategory ?: "Meal")
                    MealTag(meal.strArea ?: "General")
                }

                Spacer(modifier = Modifier.height(16.dp))

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
