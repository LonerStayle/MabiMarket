package kr.loner.mabi_market.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kr.loner.mabi_market.feature.legacy.main.MainNavigator
import kr.loner.mabi_market.feature.legacy.main.MainViewModelFactory
import kr.loner.mabi_market.ui.screen.SplashScreen
import kr.loner.mabi_market.ui.theme.MabiMarketTheme

class NewMainActivity : ComponentActivity() {
    private val viewModel by viewModels<NewMainViewModel>(factoryProducer = {
        NewMainViewModelFactory(this.applicationContext)
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MabiMarketTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = NewMainNavigator.Splash.name,
                ) {
                    composable(NewMainNavigator.Splash.name) {
                        SplashScreen {
                            navController.navigate(MainNavigator.SearchMain.name) {
                                popUpTo(MainNavigator.Splash.name) { inclusive = true }
                            }
                        }
                    }
                }
            }
        }
    }
}