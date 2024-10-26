package kr.loner.mabi_market.feature.burn_bugle_keyword

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

import kr.loner.mabi_market.ui.theme.MabiMarketTheme

class BurnBugleKeywordActivity : ComponentActivity() {
    private val viewModel by viewModels<BurnBugleKeywordViewModel>(factoryProducer = {
        BurnBugleKeywordViewModelFactory(this.applicationContext)
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MabiMarketTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = BurnBugleKeywordNavigator.List.name,
                ){

                }
            }
        }
    }
}