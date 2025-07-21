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

class report_page1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.report_page1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val nextbutton = findViewById<ImageButton>(R.id.imageButton7)
        val masseging = findViewById<ImageButton>(R.id.imageView9)
        val backbutton = findViewById<ImageButton>(R.id.imageButton98)


        val timer = findViewById<Button>(R.id.button22)
        val medis = findViewById<Button>(R.id.button21)
        val inject = findViewById<Button>(R.id.button20)
        val more = findViewById<Button>(R.id.button54)


        nextbutton.setOnClickListener {
            val intent = Intent(this, report_page3::class.java)
            startActivity(intent)
        }
        masseging.setOnClickListener {
            val intent = Intent(this, report_page4::class.java)
            startActivity(intent)
        }
        backbutton.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
        more.setOnClickListener {
            val intent = Intent(this, report_page2::class.java)
            startActivity(intent)
        }




        timer.setOnClickListener {
            val intent = Intent(this, m_scheduler_3::class.java)
            startActivity(intent)
        }

        medis.setOnClickListener {
            val intent = Intent(this, m_scheduler_4::class.java)
            startActivity(intent)
        }
        inject.setOnClickListener {
            val intent = Intent(this, m_scheduler_5::class.java)
            startActivity(intent)
        }
    }
}