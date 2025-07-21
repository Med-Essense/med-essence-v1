package com.example.test

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.jvm.java

class report_page4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_report_page4)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val quickchat = findViewById<Button>(R.id.button77)
        val backbutton = findViewById<ImageButton>(R.id.imageView42)


        quickchat.setOnClickListener {
            val intent = Intent(this, report_page5::class.java)
            startActivity(intent)
        }
        backbutton.setOnClickListener {
            val intent = Intent(this, report_page3::class.java)
            startActivity(intent)
        }

    }
}