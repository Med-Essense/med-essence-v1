package com.example.test

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.jvm.java

class m_scheduler_3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.m_scheduler)

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Setup AM/PM Spinner
        val amPmSpinner: Spinner = findViewById(R.id.amPmSpinner)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.am_pm_items,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        amPmSpinner.adapter = adapter

        val backButton = findViewById<ImageButton>(R.id.imageView29)

        backButton.setOnClickListener {
            val intent = Intent(this, m_scheduler_1::class.java)
            startActivity(intent)
            finish()
        }
    }
}
