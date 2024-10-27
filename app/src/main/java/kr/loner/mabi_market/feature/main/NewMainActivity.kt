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
import kr.loner.mabi_market.feature.legacy.main.MainNavigator
import kr.loner.mabi_market.feature.legacy.main.MainViewModelFactory
import kr.loner.mabi_market.feature.splash.SplashViewModel
import kr.loner.mabi_market.feature.splash.SplashViewModelFactory
import kr.loner.mabi_market.ui.screen.MainScreen
import kr.loner.mabi_market.ui.screen.SplashScreen
import kr.loner.mabi_market.ui.screen.legacy.SearchMainScreen
import kr.loner.mabi_market.ui.theme.MabiMarketTheme

class NewMainActivity : ComponentActivity() {
    private val viewModel by viewModels<NewMainViewModel>(factoryProducer = {
        NewMainViewModelFactory(this.applicationContext)
    })
    private val splashViewModel by viewModels<SplashViewModel>(factoryProducer = {
        SplashViewModelFactory(this.applicationContext)
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
                        SplashScreen(splashViewModel) {
                            navController.navigate(NewMainNavigator.Main.name) {
                                popUpTo(NewMainNavigator.Splash.name) { inclusive = true }
                            }
                        }
                    }

                    composable(
                        NewMainNavigator.Main.name,
                        enterTransition = { fadeIn(animationSpec = tween(0)) },
                        exitTransition = { fadeOut(animationSpec = tween(0)) },
                        popEnterTransition = { fadeIn(animationSpec = tween(0)) },
                        popExitTransition = { fadeOut(animationSpec = tween(0)) }
                    ){
                        MainScreen(viewModel)
                    }
                }
            }
        }
    }
}