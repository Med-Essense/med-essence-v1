package com.example.test

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class girl_pregnant_w24 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.girl_pregnant_w24)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val backButton = findViewById<ImageButton>(R.id.imageButton6)
        val previousbutton = findViewById<ImageButton>(R.id.imageButton8)
        val nextbutton = findViewById<ImageButton>(R.id.imageButton7)



        backButton.setOnClickListener {
            val intent = Intent(this, Vitamin_page1::class.java)
            startActivity(intent)
        }

        previousbutton.setOnClickListener {
            val intent = Intent(this, girl_pregnant_w20::class.java)
            startActivity(intent)
        }

        nextbutton.setOnClickListener {
            val intent = Intent(this, girl_pregnant_w28::class.java)
            startActivity(intent)
        }
    }
}