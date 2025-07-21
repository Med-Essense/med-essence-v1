package com.example.test

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QandApage_2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val buttonXray = findViewById<ImageButton>(R.id.imageView29)

        buttonXray.setOnClickListener {
            val intent = Intent(this, QandApage_1::class.java)
            startActivity(intent)
        }




        val chat = findViewById<ImageButton>(R.id.imageButton)

        chat.setOnClickListener {
            val intent = Intent(this, Qanda_page3::class.java)
            startActivity(intent)
        }
        val chat1 = findViewById<ImageButton>(R.id.imageButton2)

        chat1.setOnClickListener {
            val intent = Intent(this, Qanda_page3::class.java)
            startActivity(intent)
        }
        val chat2 = findViewById<ImageButton>(R.id.imageButton3)

        chat2.setOnClickListener {
            val intent = Intent(this, Qanda_page3::class.java)
            startActivity(intent)
        }
        val chat3 = findViewById<ImageButton>(R.id.imageButton4)

        chat3.setOnClickListener {
            val intent = Intent(this, Qanda_page3::class.java)
            startActivity(intent)
        }

    }
}