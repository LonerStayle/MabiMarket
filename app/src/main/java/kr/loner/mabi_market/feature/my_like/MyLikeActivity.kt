package kr.loner.mabi_market.feature.my_like

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import kr.loner.mabi_market.ui.screen.MyLikeScreen
import kr.loner.mabi_market.ui.theme.MabiMarketTheme

class MyLikeActivity: ComponentActivity() {
    private val viewModel by viewModels<MyLikeViewModel>(factoryProducer = {
        MyLikeViewModelFactory(this.applicationContext)
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MabiMarketTheme {
                MyLikeScreen(viewModel){
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        }
    }

}