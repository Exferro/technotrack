package com.example.alekseimalyshev.homework

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Window
import android.view.WindowManager
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    val splashDuration: Long = 2000
    val tickDuration: Long = 100

    var alreadyTicked: Long = 0

    var mTimer: Timer = Timer(splashDuration - alreadyTicked, tickDuration)

    fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        savedInstanceState?.run {
            alreadyTicked = getLong("ALREADY_TICKED")
        }
    }

    override fun onResume() {
        super.onResume()

        mTimer = Timer(splashDuration - alreadyTicked, tickDuration)
        mTimer.start()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong("ALREADY_TICKED", alreadyTicked)
        super.onSaveInstanceState(outState)
    }

    override fun onPause() {
        mTimer.cancel()
        super.onPause()
    }

    override fun onStop() {
        mTimer.cancel()
        super.onStop()
    }

    inner class Timer(duration: Long, tickDuration: Long) : CountDownTimer(duration, tickDuration) {

        override fun onFinish() {
            startMainActivity()
        }

        override fun onTick(value: Long) {
            alreadyTicked += tickDuration
        }
    }
}
