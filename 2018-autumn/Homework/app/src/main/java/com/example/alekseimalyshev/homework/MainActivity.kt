package com.example.alekseimalyshev.homework

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    val mAloudCounter: AloudCounter = AloudCounter(this)

    lateinit var mTextView: TextView
    lateinit var mButton: Button

    var number: Int = 0
    val maxNumber: Int = 1000

    var isCounting: Boolean = false

    val tickDuration: Long = 1000
    val countDuration: Long = maxNumber.toLong() * tickDuration

    var mTimer: Timer = Timer(countDuration - number.toLong() * tickDuration, tickDuration)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTextView = findViewById(R.id.text_view)

        mButton = findViewById(R.id.button)
        mButton.setOnClickListener(object : View.OnClickListener {
            override  fun onClick(v: View?) {
                if (isCounting) {
                    mTimer.cancel()
                    isCounting = false

                    mButton.setText(resources.getString(R.string.start))
                }
                else {
                    if (number == maxNumber)
                    {
                        number = 0
                    }
                    mTimer = Timer(countDuration - number.toLong() * tickDuration, tickDuration)
                    mTimer.start()

                    mButton.setText(resources.getString(R.string.stop))
                    isCounting = true
                }
            }
        })

        savedInstanceState?.run {
            number = getInt("NUMBER")
            isCounting = getBoolean("IS_COUNTING")
            mTextView.text = getCharSequence("TEXT")
        }

        if (isCounting) {
            mButton.text = resources.getString(R.string.stop)
        }
        else {
            mButton.text = resources.getString(R.string.start)
        }
    }

    override fun onResume() {
        super.onResume()

        if (isCounting) {
            mTimer = Timer(countDuration - number.toLong() * tickDuration, tickDuration)
            mTimer.start()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("NUMBER", number)
        outState.putBoolean("IS_COUNTING", isCounting)
        outState.putCharSequence("TEXT", mTextView.text)
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
            number += 1
            mTextView.setText(mAloudCounter.sayAloud(number))

            isCounting = false
            mButton.setText(resources.getString(R.string.start))
        }

        override fun onTick(value: Long) {
            number += 1
            mTextView.setText(mAloudCounter.sayAloud(number))
        }
    }
}
