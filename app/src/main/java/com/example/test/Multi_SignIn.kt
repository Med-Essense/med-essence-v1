package com.example.test

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class Multi_SignIn : AppCompatActivity() {

    private lateinit var monthText: TextView
    private lateinit var weekText: TextView
    private lateinit var dayText: TextView

    private lateinit var monthLayout: View
    private lateinit var weekLayout: View
    private lateinit var dayLayout: View

    private var months = 0
    private var weeks = 0
    private var days = 0
    private var selectedGender: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.multi_signin)

        val selectorSpinner: Spinner = findViewById(R.id.selectorSpinner)
        val options = arrayOf("Month", "Week", "Day")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        selectorSpinner.adapter = adapter

        val backbutton = findViewById<ImageButton>(R.id.imageButton89)
        val boyButton = findViewById<ImageButton>(R.id.imageButton91)
        val girlButton = findViewById<ImageButton>(R.id.imageButton92)
        val addDetailsButton = findViewById<Button>(R.id.button32)
        val skipButton = findViewById<Button>(R.id.button31)

        backbutton.setOnClickListener {
            startActivity(Intent(this, Vitamin_page1::class.java))
        }

        boyButton.setOnClickListener {
            selectedGender = "boy"
            boyButton.setBackgroundResource(R.drawable.selected_gender_background)
            girlButton.setBackgroundResource(android.R.color.transparent)
        }

        girlButton.setOnClickListener {
            selectedGender = "girl"
            girlButton.setBackgroundResource(R.drawable.selected_gender_background)
            boyButton.setBackgroundResource(android.R.color.transparent)
        }

        skipButton.setOnClickListener {
            startActivity(Intent(this, girl_pregnant_w36::class.java))
        }

        monthLayout = findViewById(R.id.monthLayout)
        weekLayout = findViewById(R.id.weekLayout)
        dayLayout = findViewById(R.id.dayLayout)

        monthText = findViewById(R.id.monthText)
        weekText = findViewById(R.id.weekText)
        dayText = findViewById(R.id.dayText)

        // Month controls
        findViewById<Button>(R.id.monthPlus).setOnClickListener {
            months++
            updateCounter(monthText, months)
        }
        findViewById<Button>(R.id.monthMinus).setOnClickListener {
            if (months > 0) months--
            updateCounter(monthText, months)
        }

        // Week controls
        findViewById<Button>(R.id.weekPlus).setOnClickListener {
            weeks++
            updateCounter(weekText, weeks)
        }
        findViewById<Button>(R.id.weekMinus).setOnClickListener {
            if (weeks > 0) weeks--
            updateCounter(weekText, weeks)
        }

        // Day controls
        findViewById<Button>(R.id.dayPlus).setOnClickListener {
            days++
            updateCounter(dayText, days)
        }
        findViewById<Button>(R.id.dayMinus).setOnClickListener {
            if (days > 0) days--
            updateCounter(dayText, days)
        }

        selectorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                when (options[position]) {
                    "Month" -> {
                        monthLayout.visibility = View.VISIBLE
                        weekLayout.visibility = View.VISIBLE
                        dayLayout.visibility = View.VISIBLE
                    }
                    "Week" -> {
                        monthLayout.visibility = View.GONE
                        weekLayout.visibility = View.VISIBLE
                        dayLayout.visibility = View.VISIBLE
                    }
                    "Day" -> {
                        monthLayout.visibility = View.GONE
                        weekLayout.visibility = View.GONE
                        dayLayout.visibility = View.VISIBLE
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        addDetailsButton.setOnClickListener {
            if (selectedGender == null) {
                Toast.makeText(this, "Please select baby's gender", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val totalWeeks = (months * 4) + weeks + (days / 7)

            if (totalWeeks <= 0) {
                Toast.makeText(this, "Please enter baby's age", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Total weeks: $totalWeeks", Toast.LENGTH_SHORT).show()

            when (selectedGender) {
                "boy" -> navigateToBoyActivity(totalWeeks)
                "girl" -> navigateToGirlActivity(totalWeeks)
            }
        }
    }

    private fun updateCounter(textView: TextView, value: Int) {
        textView.text = value.toString()
    }

    private fun navigateToBoyActivity(weeks: Int) {
        val intent = when (weeks) {
            1 -> Intent(this, boy_pregnant_w1::class.java)
            2 -> Intent(this, boy_pregnant_w2::class.java)
            3 -> Intent(this, boy_pregnant_w3::class.java)
            4 -> Intent(this, boy_pregnant_w4::class.java)
            5 -> Intent(this, boy_pregnant_w5::class.java)
            6 -> Intent(this, boy_pregnant_w6::class.java)
            7 -> Intent(this, boy_pregnant_w7::class.java)
            8 -> Intent(this, boy_pregnant_w8::class.java)
            9 -> Intent(this, boy_pregnant_w9::class.java)
            10 -> Intent(this, boy_pregnant_w10::class.java)
            11 -> Intent(this, boy_pregnant_w11::class.java)
            12 -> Intent(this, boy_pregnant_w12::class.java)
            in 13..15 -> Intent(this, boy_pregnant_w16::class.java)
            in 16..19 -> Intent(this, boy_pregnant_w16::class.java)
            in 20..23 -> Intent(this, boy_pregnant_w20::class.java)
            in 24..27 -> Intent(this, boy_pregnant_w24::class.java)
            in 28..31 -> Intent(this, boy_pregnant_w28::class.java)
            in 32..35 -> Intent(this, boy_pregnant_w32::class.java)
            in 36..40 -> Intent(this, boy_pregnant_w36::class.java)
            else -> Intent(this, boy_pregnant_w1::class.java)
        }

        intent.putExtra("WEEKS", weeks)
        startActivity(intent)
    }

    private fun navigateToGirlActivity(weeks: Int) {
        val intent = when (weeks) {
            1 -> Intent(this, girl_pregnant_w1::class.java)
            2 -> Intent(this, girl_pregnant_w2::class.java)
            3 -> Intent(this, girl_pregnant_w3::class.java)
            4 -> Intent(this, girl_pregnant_w4::class.java)
            5 -> Intent(this, girl_pregnant_w5::class.java)
            6 -> Intent(this, girl_pregnant_w6::class.java)
            7 -> Intent(this, girl_pregnant_w7::class.java)
            8 -> Intent(this, girl_pregnant_w8::class.java)
            9 -> Intent(this, girl_pregnant_w9::class.java)
            10 -> Intent(this, girl_pregnant_w10::class.java)
            11 -> Intent(this, girl_pregnant_w11::class.java)
            12 -> Intent(this, girl_pregnant_w12::class.java)
            in 13..15 -> Intent(this, girl_pregnant_w16::class.java)
            in 16..19 -> Intent(this, girl_pregnant_w16::class.java)
            in 20..23 -> Intent(this, girl_pregnant_w20::class.java)
            in 24..27 -> Intent(this, girl_pregnant_w24::class.java)
            in 28..31 -> Intent(this, girl_pregnant_w28::class.java)
            in 32..35 -> Intent(this, girl_pregnant_w32::class.java)
            in 36..40 -> Intent(this, girl_pregnant_w36::class.java)
            else -> Intent(this, girl_pregnant_w1::class.java)
        }

        intent.putExtra("WEEKS", weeks)
        startActivity(intent)
    }
}
