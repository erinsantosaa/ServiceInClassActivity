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

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null && intent.hasExtra("COUNTDOWN_VALUE")) {
            countdownValue = intent.getIntExtra("COUNTDOWN_VALUE", 0)
            startCountdown()
        }
        return START_NOT_STICKY
    }

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

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

}
