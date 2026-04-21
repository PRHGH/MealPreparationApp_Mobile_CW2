package com.example.mealpreparationapp

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import com.example.mealpreparationapp.model.Meal

val MealSaver: Saver<Meal, Any> = listSaver(
    save = { meal ->
        listOf(
            meal.idMeal,
            meal.strMeal,
            meal.strDrinkAlternate,
            meal.strCategory,
            meal.strArea,
            meal.strInstructions,
            meal.strTags,
            meal.strYoutube,
            meal.strMealThumb,

            meal.strIngredient1,
            meal.strIngredient2,
            meal.strIngredient3,
            meal.strIngredient4,
            meal.strIngredient5,
            meal.strIngredient6,
            meal.strIngredient7,
            meal.strIngredient8,
            meal.strIngredient9,
            meal.strIngredient10,
            meal.strIngredient11,
            meal.strIngredient12,
            meal.strIngredient13,
            meal.strIngredient14,
            meal.strIngredient15,
            meal.strIngredient16,
            meal.strIngredient17,
            meal.strIngredient18,
            meal.strIngredient19,
            meal.strIngredient20,

            meal.strMeasure1,
            meal.strMeasure2,
            meal.strMeasure3,
            meal.strMeasure4,
            meal.strMeasure5,
            meal.strMeasure6,
            meal.strMeasure7,
            meal.strMeasure8,
            meal.strMeasure9,
            meal.strMeasure10,
            meal.strMeasure11,
            meal.strMeasure12,
            meal.strMeasure13,
            meal.strMeasure14,
            meal.strMeasure15,
            meal.strMeasure16,
            meal.strMeasure17,
            meal.strMeasure18,
            meal.strMeasure19,
            meal.strMeasure20
        )
    },
    restore = { values ->
        Meal(
            idMeal = values[0] as String,
            strMeal = values[1] as String?,
            strDrinkAlternate = values[2] as String?,
            strCategory = values[3] as String?,
            strArea = values[4] as String?,
            strInstructions = values[5] as String?,
            strTags = values[6] as String?,
            strYoutube = values[7] as String?,
            strMealThumb = values[8] as String?,

            strIngredient1 = values[9] as String?,
            strIngredient2 = values[10] as String?,
            strIngredient3 = values[11] as String?,
            strIngredient4 = values[12] as String?,
            strIngredient5 = values[13] as String?,
            strIngredient6 = values[14] as String?,
            strIngredient7 = values[15] as String?,
            strIngredient8 = values[16] as String?,
            strIngredient9 = values[17] as String?,
            strIngredient10 = values[18] as String?,
            strIngredient11 = values[19] as String?,
            strIngredient12 = values[20] as String?,
            strIngredient13 = values[21] as String?,
            strIngredient14 = values[22] as String?,
            strIngredient15 = values[23] as String?,
            strIngredient16 = values[24] as String?,
            strIngredient17 = values[25] as String?,
            strIngredient18 = values[26] as String?,
            strIngredient19 = values[27] as String?,
            strIngredient20 = values[28] as String?,

            strMeasure1 = values[29] as String?,
            strMeasure2 = values[30] as String?,
            strMeasure3 = values[31] as String?,
            strMeasure4 = values[32] as String?,
            strMeasure5 = values[33] as String?,
            strMeasure6 = values[34] as String?,
            strMeasure7 = values[35] as String?,
            strMeasure8 = values[36] as String?,
            strMeasure9 = values[37] as String?,
            strMeasure10 = values[38] as String?,
            strMeasure11 = values[39] as String?,
            strMeasure12 = values[40] as String?,
            strMeasure13 = values[41] as String?,
            strMeasure14 = values[42] as String?,
            strMeasure15 = values[43] as String?,
            strMeasure16 = values[44] as String?,
            strMeasure17 = values[45] as String?,
            strMeasure18 = values[46] as String?,
            strMeasure19 = values[47] as String?,
            strMeasure20 = values[48] as String?
        )
    }
)

val MealListSaver: Saver<List<Meal>, Any> = listSaver(
    save = { meals ->
        meals.map { meal ->
            with(MealSaver) {
                this@listSaver.save(meal)
            }
        }
    },
    restore = { saved ->
        saved.map { MealSaver.restore(it!!)!! }
    }
)