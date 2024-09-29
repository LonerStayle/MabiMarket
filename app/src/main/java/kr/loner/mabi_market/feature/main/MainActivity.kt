package kr.loner.mabi_market.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kr.loner.mabi_market.ui.screen.SearchFilterScreen
import kr.loner.mabi_market.ui.screen.SearchMainScreen
import kr.loner.mabi_market.ui.screen.SearchScreen
import kr.loner.mabi_market.ui.screen.SplashScreen
import kr.loner.mabi_market.ui.theme.MabiMarketTheme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>(factoryProducer = {
        MainViewModelFactory(this.applicationContext)
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MabiMarketTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = MainNavigator.Splash.name,
                ){
                    composable(MainNavigator.Splash.name){
                        SplashScreen{
                            navController.navigate(MainNavigator.SearchMain.name){
                                popUpTo(MainNavigator.Splash.name) { inclusive = true }
                            }
                        }
                    }

                    composable(
                        MainNavigator.SearchMain.name,
                        enterTransition = { fadeIn(animationSpec = tween(0)) },
                        exitTransition = { fadeOut(animationSpec = tween(0)) },
                        popEnterTransition = { fadeIn(animationSpec = tween(0)) },
                        popExitTransition = { fadeOut(animationSpec = tween(0)) }
                    ){
                        SearchMainScreen(
                            mainViewModel = viewModel,
                            openFilter = {
                            navController.navigate(MainNavigator.SearchFilter.name)
                        }, openSearch = {
                            navController.navigate(MainNavigator.Search.name)
                        })
                    }

                    composable(MainNavigator.Search.name){
                        SearchScreen(viewModel) { navController.popBackStack() }
                    }

                    composable(MainNavigator.SearchFilter.name){
                        SearchFilterScreen(viewModel) {
                            navController.popBackStack()
                        }
                    }
                }
            }
        }
    }
}

