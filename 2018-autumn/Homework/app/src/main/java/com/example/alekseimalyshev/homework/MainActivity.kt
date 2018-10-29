package com.example.alekseimalyshev.homework

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView

/**
 * A class which implements the app Main Activity (which is used for counting)
 */
class MainActivity : AppCompatActivity() {

    // An instance of AloudCounter used to "pronounce" numbers
    val mAloudCounter: AloudCounter = AloudCounter(this)

    // Two elements of the activity layout
    lateinit var mTextView: TextView
    lateinit var mButton: Button

    // A number used for counting and its maximum value
    var number: Int = 0
    val maxNumber: Int = 1000

    // A variable which shows whether an activity counts at the moment
    var isCounting: Boolean = false

    // Settings for the counting (frequency and overall time)
    val tickDuration: Long = 1000
    val countDuration: Long = maxNumber.toLong() * tickDuration

    // A timer used for counting
    var mTimer: Timer = Timer(countDuration - number.toLong() * tickDuration, tickDuration)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTextView = findViewById(R.id.text_view)
        mButton = findViewById(R.id.button)

        mButton.setOnClickListener {
                // If we are counting at the moment and the button press means "STOP"
                if (isCounting) {
                    // We stop counting
                    mTimer.cancel()
                    isCounting = false

                    // Set button state to "START"
                    mButton.setText(resources.getString(R.string.start))
                }
                // Otherwise, button is in the state "START" and we want to start counting
                else {
                    // If we have already reached maximum of number, we start counting from
                    // the beginning
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
