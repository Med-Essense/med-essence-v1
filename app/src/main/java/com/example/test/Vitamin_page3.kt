package com.example.test
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


import kotlin.jvm.java

class Vitamin_page3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vitamin_page3)

        // Get the button
        val suggestedButton: Button = findViewById(R.id.button25)

        // Set click listener
        suggestedButton.setOnClickListener {
            // Show a toast message or go to another page
            Toast.makeText(this, "Loading suggested recipes...", Toast.LENGTH_SHORT).show()

            // Navigate to RecipeSuggestionActivity (Create this separately)
            val intent = Intent(this, RecipeSuggestionActivity::class.java)
            startActivity(intent)
        }
    }
}
