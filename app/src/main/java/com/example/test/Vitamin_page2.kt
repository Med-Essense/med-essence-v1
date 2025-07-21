package com.example.test

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.jvm.java

class Vitamin_page2 : AppCompatActivity() {

    private lateinit var searchBox: EditText
    private lateinit var additionalInfo: EditText
    private lateinit var backButton: ImageButton
    private lateinit var profileButton: ImageButton
    private lateinit var countrySpinner: Spinner
    private lateinit var itemSpinner: Spinner
    private lateinit var foodTypeSpinner: Spinner
    private lateinit var recipeButton: Button
    private lateinit var favoriteButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vitamin_page_2)

        // Bind views
        searchBox = findViewById(R.id.editTextTextMultiLine2)
        additionalInfo = findViewById(R.id.editTextTextMultiLine)
        backButton = findViewById(R.id.imageButton38)
        profileButton = findViewById(R.id.imageButton42)
        countrySpinner = findViewById(R.id.mySpinner2)
        itemSpinner = findViewById(R.id.mySpinner3)
        foodTypeSpinner = findViewById(R.id.mySpinner)
        recipeButton = findViewById(R.id.button12)
        favoriteButton = findViewById(R.id.button13)

        // Spinner options
        val countries = listOf("Select Country", "Sri Lanka", "USA", "India", "UK")
        val foodTypes = listOf("Select food type", "veg", "non-veg")
        val vegItems = listOf("Select Item", "Carrot", "Spinach", "Tomato", "Broccoli")
        val nonVegItems = listOf("Select Item", "Chicken", "Beef", "Fish", "Eggs")

        // Set adapters
        countrySpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, countries)
        foodTypeSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, foodTypes)
        itemSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listOf("Select Item"))

        // Update items based on food type selection
        foodTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedType = foodTypes[position]
                val items = when (selectedType) {
                    "veg" -> vegItems
                    "non-veg" -> nonVegItems
                    else -> listOf("Select Item")
                }
                itemSpinner.adapter = ArrayAdapter(this@Vitamin_page2, android.R.layout.simple_spinner_dropdown_item, items)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Back button
        backButton.setOnClickListener {
            finish() // Simply go back
        }

        // Profile button
        profileButton.setOnClickListener {
            Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
        }

        // Recipe button logic
        recipeButton.setOnClickListener {
            val selectedType = foodTypeSpinner.selectedItem.toString()
            val selectedCountry = countrySpinner.selectedItem.toString()
            val selectedItem = itemSpinner.selectedItem.toString()
            val description = additionalInfo.text.toString()

            if (selectedType == "Select food type" || selectedCountry == "Select Country" || selectedItem == "Select Item") {
                Toast.makeText(this, "Please select all fields", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Suggestions generated!", Toast.LENGTH_SHORT).show()
                // TODO: Navigate to suggestions screen or display results
                val intent = Intent(this, Vitamin_page3::class.java)
                startActivity(intent)
            }
        }

        // Favorite button logic
        favoriteButton.setOnClickListener {
            Toast.makeText(this, "Opening favorite recipes...", Toast.LENGTH_SHORT).show()
            // TODO: Navigate to favorites screen
        }
    }
}
