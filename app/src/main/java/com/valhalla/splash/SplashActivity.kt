package com.valhalla.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.valhalla.util.observeIn
import com.valhalla.MainActivity
import com.valhalla.MainActivity.Companion.EXTRA_START_SCREEN
import com.valhalla.MainActivity.Companion.START_MAIN
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview

@SuppressLint("CustomSplashScreen")
@FlowPreview
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.actions.observeIn(this) {
            MainActivity.openActivity(this, Bundle().apply {
                putString(
                    EXTRA_START_SCREEN,
                    START_MAIN
                )
            })
            finishAffinity()
        }
    }
}
