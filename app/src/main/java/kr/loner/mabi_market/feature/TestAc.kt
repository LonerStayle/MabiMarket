package kr.loner.mabi_market.feature

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.UserRecoverableAuthException
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import kr.loner.mabi_market.data.ServiceLocator
import kr.loner.mabi_market.data.network.FcmApi

class TestAc : ComponentActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {  }

//        lifecycleScope.launch {
//            ServiceLocator.getFcmApi().sendNotification(
//                notification =  FcmApi.Message(
//                    to = "evzl60buSqqbkTx5ni7egV:APA91bEvE_EZGHAA4hsZlDmT2JfJpojgZzLNjPD5kgJsHvJAMlDH_5HIeCKwbhd8TQDrqQPVBKcgD7Kvw7RVRRflOR639GKuOTL4hx7J-39Oj5bf2eVdpzo",
//                    data = FcmApi.NotificationData(
//                        title = "testTitle",
//                        content = "bodybody"
//                    )
//                )
//            )
//        }



        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if(task.isSuccessful){

            }
        }


        // Google Sign-In 옵션 설정
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("58959327663-p0b4rvf44su6iqcetna1o8chshnokuba.apps.googleusercontent.com") // Google Cloud Console에서 OAuth 클라이언트 ID를 입력
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Google 계정으로 로그인
        signIn()
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            account?.let {
                // Access Token 얻기
                fetchAccessToken(it)
            }
        } catch (e: ApiException) {
            Log.w("MainActivity", "Google sign-in failed", e)
        }
    }

    private fun fetchAccessToken(account: GoogleSignInAccount) {
        Thread {
            try {
                val accessToken = account.account?.let {
                    GoogleAuthUtil.getToken(
                        this@TestAc,
                        it,
                        "oauth2:https://www.googleapis.com/auth/firebase.messaging"
                    )
                }
//                Log.d("MainActivity", "Access Token: $accessToken")
                // Retrofit Authorization 헤더에 이 토큰을 추가해 사용
            } catch (e: UserRecoverableAuthException) {
                e.intent?.let { startActivityForResult(it, RC_SIGN_IN) }
            } catch (e: Exception) {
                Log.e("MainActivity", "Failed to fetch access token", e)
            }
        }.start()
    }


}