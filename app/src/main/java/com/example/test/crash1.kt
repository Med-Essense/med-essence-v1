package com.example.test

import android.R.attr.button
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.jvm.java

class crash1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.crash1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val backbutton = findViewById<ImageButton>(R.id.Backbutton)
        val crashdetection = findViewById<Button>(R.id.button)


        backbutton.setOnClickListener {
            val intent = Intent(this, Home ::class.java)
            startActivity(intent)
        }

        crashdetection.setOnClickListener {

            val intent = Intent(this, crash2 ::class.java)

            startActivity(intent)
        }
    }
}