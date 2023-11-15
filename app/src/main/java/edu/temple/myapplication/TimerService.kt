package edu.temple.myapplication

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class TimerService : Service() {

    private var countdownValue: Int = 0
    private var isRunning: Boolean = false

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    private fun startCountdown() {
        if (!isRunning) {
            isRunning = true
            scope.launch {
                for (i in countdownValue downTo 1) {
                    Log.d("Countdown", i.toString())
                    delay(1000)
                }
                stopSelf()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}
