package kr.loner.mabi_market.feature

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import kr.loner.mabi_market.feature.service.AdminService

class AdminActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, AdminService::class.java)
        startService(intent)
    }
}