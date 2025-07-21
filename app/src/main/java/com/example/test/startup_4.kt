package com.example.test

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class startup_4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.startup_4)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 👉 Button logic goes here
        val button1: ImageButton = findViewById(R.id.imageButton)
        val button2: ImageButton = findViewById(R.id.imageButton2)
        val button3: ImageButton = findViewById(R.id.imageView7)

        button1.setOnClickListener {
            val intent = Intent(this, startup_5::class.java)
            startActivity(intent)
        }
        button2.setOnClickListener {

            val intent = Intent(this, startup_6::class.java)
            startActivity(intent)
        }
        button3.setOnClickListener {
            val intent = Intent(this, startup_3::class.java)
            startActivity(intent)
        }
    }
}
