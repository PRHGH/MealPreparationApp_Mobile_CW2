package com.example.mealpreparationapp

// Activity recreated on rotation
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.room.Room
import com.example.mealpreparationapp.ui.theme.MealPreparationAppTheme

class MainActivity : ComponentActivity() {

    private lateinit var db: AppDatabase
    private lateinit var mealDao: MealDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Init Room DB
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "meal_db"
        ).build()

        mealDao = db.mealDao()

        setContent {
            MealPreparationAppTheme {
                // rememberSaveable = rotation safe
                var currentScreen by rememberSaveable { mutableStateOf(AppScreen.HOME) }

                // Compose Navigation logic
                when (currentScreen) {
                    AppScreen.HOME -> HomeScreen(
                        onAddMealsClick = { currentScreen = AppScreen.ADD_MEALS },
                        onSearchByIngredientClick = { currentScreen = AppScreen.SEARCH_BY_INGREDIENT },
                        onSearchMealsClick = { currentScreen = AppScreen.SEARCH_MEALS },
                        onSearchMealsOnlineClick = { currentScreen = AppScreen.SEARCH_MEALS_ONLINE }
                    )
                    AppScreen.ADD_MEALS -> AddMealsScreen(
                        mealDao = mealDao,
                        onBackClick = { currentScreen = AppScreen.HOME }
                    )
                    AppScreen.SEARCH_BY_INGREDIENT -> SearchByIngredientScreen(
                        mealDao = mealDao,
                        onBackClick = { currentScreen = AppScreen.HOME }
                    )
                    AppScreen.SEARCH_MEALS -> SearchMealsScreen(
                        mealDao = mealDao,
                        onBackClick = { currentScreen = AppScreen.HOME }
                    )
                    AppScreen.SEARCH_MEALS_ONLINE -> SearchMealsOnlineScreen(
                        onBackClick = { currentScreen = AppScreen.HOME }
                    )
                }
            }
        }
    }
}

enum class AppScreen {
    HOME,
    ADD_MEALS,
    SEARCH_BY_INGREDIENT,
    SEARCH_MEALS,
    SEARCH_MEALS_ONLINE
}
