package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivitySP : AppCompatActivity() {
    private lateinit var textSecondsElapsed: TextView
    private var secondsElapsed: Int = 0
    private lateinit var sharedPref: SharedPreferences
    @Volatile
    private var stopIt = false

    private val backgroundThread = Thread {
        while (true) {
            if (!stopIt) {
                textSecondsElapsed.post {
                    textSecondsElapsed.text = "Seconds elapsed: $secondsElapsed"
                }
                Thread.sleep(1000)
                secondsElapsed++
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        if (savedInstanceState == null) {
            with(sharedPref.edit()) {
                putInt("secondsElapsed", 0)
                apply()
            }
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
    }

    override fun onResume() {
        secondsElapsed = sharedPref.getInt("secondsElapsed", 0)
        super.onResume()
    }

    override fun onPause() {
        with(sharedPref.edit()) {
            putInt("secondsElapsed", secondsElapsed)
            apply()
        }
        super.onPause()
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
