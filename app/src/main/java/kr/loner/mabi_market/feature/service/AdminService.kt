package kr.loner.mabi_market.feature.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kr.loner.mabi_market.R
import kr.loner.mabi_market.data.ServiceLocator
import kr.loner.mabi_market.data.model.ServerType

class AdminService : Service() {
    private val CHANNEL_ID = "my_foreground_service_channel"

    private val mabinogiApi = ServiceLocator.getMabinogiApi()
    private val dataStore by lazy { ServiceLocator.getNewDataStore(this) }

    private val serviceJob = Job() // Job 관리
    private val serviceScope = CoroutineScope(Dispatchers.Default + serviceJob) // CoroutineScope 설정

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel() // 채널 생성 호출
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("My Service")
            .setContentText("Running in the foreground")
            .setSmallIcon(R.drawable.ic_icon)
            .build()

        startRepeatingTask()
        startForeground(1, notification)
    }

    private fun startRepeatingTask() {
        serviceScope.launch {
            while (true) {
                performTask() // 실제 작업 수행
                delay(5 * 60 * 1000) // 5분 대기
            }
        }
    }

    private suspend fun performTask() {
        // 수행할 작업을 여기서 구현
        // 예: Log.d("MyForegroundService", "Task performed")


        val chatLikeList = dataStore.bornWorldChatLikeListFlow.first()
        val serverList = chatLikeList.map { it.serverType }.distinct()

        serverList.forEach {
            val historyList = mabinogiApi.getBornBugleWorldHistories(it.name).hornBugleWorldHistory

            chatLikeList.forEach { findKeyword ->
                historyList.forEach { history ->
                    if (history.message.contains(findKeyword.keyword)) {

                    }
                }
            }
        }

    }

    private fun createNotificationChannel() {
        val channelName = "My Foreground Service Channel"
        val channelDescription = "Channel for foreground service notifications"
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        val channel = NotificationChannel(CHANNEL_ID, channelName, importance).apply {
            description = channelDescription
        }

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}