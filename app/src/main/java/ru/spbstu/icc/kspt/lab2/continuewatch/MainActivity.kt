package ru.spbstu.icc.kspt.lab2.continuewatch

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {
    private lateinit var textSecondsElapsed: TextView
    lateinit var logger: Logger
    private var secondsElapsed: Int = 0
    private var stopit = false

    var backgroundThread = Thread {
        while (true) {
            Thread.sleep(1000)
            textSecondsElapsed.post {
                textSecondsElapsed.text = if (stopit) "Seconds elapsed:${secondsElapsed} " else "Seconds elapsed:${secondsElapsed++} "
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
        logger = Logger.getLogger(packageName)
        logger.info("onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("seconds_elapsed", secondsElapsed)
        logger.info("onSaveInstanceState")
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        logger.info("onRestoreInstanceState")
        savedInstanceState.getInt("seconds_elapsed")
    }

    override fun onResume() {
        logger.info("onResume")
        stopit = false
        super.onResume()
    }

    override fun onPause() {
        logger.info("onPause")
        stopit = true
        super.onPause()
    }
}
