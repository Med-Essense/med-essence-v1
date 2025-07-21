package com.example.test

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class report_page3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.report_page3)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val aisug = findViewById<Button>(R.id.button75)  // Button to go to report_page2
        val ai = findViewById<Button>(R.id.button76)     // Button to go to report_page4
        val backbutton = findViewById<ImageButton>(R.id.imageView77)
        aisug.setOnClickListener {
            val intent = Intent(this, report_page2::class.java)
            startActivity(intent)
        }

        ai.setOnClickListener {
            val intent = Intent(this, report_page4::class.java)
            startActivity(intent)
        }
        backbutton.setOnClickListener {
            val intent = Intent(this, report_page1::class.java)
            startActivity(intent)
        }
    }
}
