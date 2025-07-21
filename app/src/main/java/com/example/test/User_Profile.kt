package com.example.test

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class User_Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.user_profile)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

              // e.g. Family link
        val imageButton2 = findViewById<ImageButton>(R.id.imageButton19) // e.g. Map
        val homepage = findViewById<ImageButton>(R.id.imageButton75)
        val ai = findViewById<ImageButton>(R.id.imageButton76)
        val familyLink = findViewById<Button>(R.id.button24)
// e.g.


        imageButton2.setOnClickListener {
            val intent = Intent(this, map::class.java)
            startActivity(intent)
        }
        ai.setOnClickListener {
            val intent = Intent(this, map::class.java)
            startActivity(intent)
        }

        homepage.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
        familyLink.setOnClickListener {
            val intent = Intent(this, family_link::class.java)
            startActivity(intent)
        }
    }
}
