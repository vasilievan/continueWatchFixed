package ru.spbstu.icc.kspt.lab2.continuewatch

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var textSecondsElapsed: TextView
    private var secondsElapsed: Int = 0
    private var stopIt = false

    private val backgroundThread = Thread {
        while (true) {
            if (!stopIt) {
                Thread.sleep(1000)
                textSecondsElapsed.post {
                    textSecondsElapsed.text = "Seconds elapsed: ${secondsElapsed++}"
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                secondsElapsed = getInt("seconds_elapsed")
            }
        } else {
            secondsElapsed = 0
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("seconds_elapsed", secondsElapsed)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getInt("seconds_elapsed")
    }

    override fun onStart() {
        stopIt = false
        super.onStart()
    }

    override fun onStop() {
        stopIt = true
        super.onStop()
    }
}
