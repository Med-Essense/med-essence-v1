package com.example.test

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class startup_2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.startup_2)

        // Handle system window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Button and ImageButton references
        val imageButton1 = findViewById<ImageButton>(R.id.imageView16)    // Assuming this is a MaterialButton or Button
        val imageButton2 = findViewById<ImageButton>(R.id.imageView7)

        // Set click listeners
        imageButton1.setOnClickListener {
            val intent = Intent(this, startup_3::class.java) // Correct class name casing
            startActivity(intent)
        }

        imageButton2.setOnClickListener {
            val intent = Intent(this, startup_1::class.java) // Correct class name casing
            startActivity(intent)
        }
    }
}
