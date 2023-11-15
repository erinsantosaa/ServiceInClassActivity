package edu.temple.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.editText)
        val startButton = findViewById<Button>(R.id.startButton)

        startButton.setOnClickListener {
            val userInput = editText.text.toString()
            if (userInput.isNotEmpty()) {
                val intent = Intent(this, TimerService::class.java)
                intent.putExtra("COUNTDOWN_VALUE", userInput.toInt())
                startService(intent)
            }
        }
    }
}
