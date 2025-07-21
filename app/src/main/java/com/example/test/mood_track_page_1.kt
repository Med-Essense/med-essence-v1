package com.example.test

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class mood_track_page_1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.mood_track_page_1)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val music = findViewById<Button>(R.id.button14)

        val dreamLogButton = findViewById<Button>(R.id.button57)

        val startupButton2 = findViewById<Button>(R.id.button58)
        val backbatton = findViewById<ImageButton>(R.id.imageButton46)

        dreamLogButton.setOnClickListener {
            val intent = Intent(this, dreem_log_page1::class.java)
            startActivity(intent)
        }

        music.setOnClickListener {
            val intent = Intent(this, mood_track_page_2::class.java)
            startActivity(intent)
        }

        startupButton2.setOnClickListener {
            val intent = Intent(this, dreem_log_page1::class.java)
            startActivity(intent)
        }
        backbatton.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
    }
}
