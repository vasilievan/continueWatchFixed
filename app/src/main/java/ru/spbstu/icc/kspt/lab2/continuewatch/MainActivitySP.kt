package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.logging.Logger

class MainActivitySP : AppCompatActivity() {
    private lateinit var textSecondsElapsed: TextView
    lateinit var logger: Logger
    private var secondsElapsed: Int = 0
    private var stopit = false
    private lateinit var sharedPref: SharedPreferences

    var backgroundThread = Thread {
        while (true) {
            Thread.sleep(1000)
            textSecondsElapsed.post {
                textSecondsElapsed.text = if (stopit) "Seconds elapsed:${secondsElapsed} " else "Seconds elapsed:${secondsElapsed++} "
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        if (savedInstanceState == null) {
            with (sharedPref.edit()) {
                putInt("secondsElapsed", 0)
                apply()
            }
        }
        logger = Logger.getLogger(packageName)
        logger.info("onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
    }

    override fun onResume() {
        secondsElapsed = sharedPref.getInt("secondsElapsed", 0)
        logger.info("onResume")
        stopit = false
        super.onResume()
    }

    override fun onPause() {
        with (sharedPref.edit()) {
            putInt("secondsElapsed", secondsElapsed)
            apply()
        }
        logger.info("onPause")
        stopit = true
        super.onPause()
    }
}
