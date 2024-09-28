package kr.loner.mabi_market.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kr.loner.mabi_market.ui.screen.SearchMainScreen
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
                            navController.navigate(MainNavigator.SearchMain.name)
                        }
                    }

                    composable(MainNavigator.SearchMain.name){
                        SearchMainScreen(viewModel)
                    }
                }
            }
        }
    }
}

