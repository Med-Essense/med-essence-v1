package com.example.test

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class startup_1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.startup_1)

        // Handle system window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ImageButton click to navigate to SecondActivity
        val imageButton = findViewById<ImageButton>(R.id.imageView16)
        val imageButton2 = findViewById<ImageButton>(R.id.imageView7)
        imageButton.setOnClickListener {
            val intent = Intent(this, startup_2::class.java)
            startActivity(intent)
        }
        imageButton2.setOnClickListener {
            val intent = Intent(this, startup_1::class.java)
            startActivity(intent)
        }
    }
}
