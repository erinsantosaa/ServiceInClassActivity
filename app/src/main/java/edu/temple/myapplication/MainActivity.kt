package edu.temple.myapplication

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.Button

class MainActivity : AppCompatActivity() {
    var isConnected = false
    lateinit var timerBinder: TimerService.TimerBinder

    val serviceConnection = object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            timerBinder = service as TimerService.TimerBinder
            timerBinder.setHandler(timerHandler)
            isConnected = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isConnected = false
        }

    }

    val timerHandler = Handler(Looper.getMainLooper()){
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindService(
            Intent(this, TimerService::class.java),
            serviceConnection,
            BIND_AUTO_CREATE)

        findViewById<Button>(R.id.startButton).setOnClickListener {
            if(isConnected)
                timerBinder.start(100)
        }

        findViewById<Button>(R.id.pauseButton).setOnClickListener {
            if(isConnected)
                timerBinder.pause()
        }
        
        findViewById<Button>(R.id.stopButton).setOnClickListener {
            if(isConnected)
                timerBinder.stop()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_play -> {
                if (isConnected) {
                    timerBinder.start(100)
                }
                return true
            }
            R.id.action_pause -> {
                if (isConnected) {
                    timerBinder.pause()
                }
                return true
            }
            R.id.action_stop -> {
                if (isConnected) {
                    timerBinder.stop()
                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}