package com.example.test

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.test.database.DreamDatabaseHelper
import com.example.test.model.DreamEntry
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.take

class dreem_log_page1 : AppCompatActivity() {

    private lateinit var databaseHelper: DreamDatabaseHelper
    private val dreamContainers = mutableListOf<ConstraintLayout>()
    private val dateButtons = mutableListOf<Button>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dreem_log_page1)

        databaseHelper = DreamDatabaseHelper(this)

        // Initialize containers
        dreamContainers.addAll(listOf(
            findViewById(R.id.constraintLayout45),
            findViewById(R.id.constraintLayout44),
            findViewById(R.id.constraintLayout43)
        ))
        val backbuttton = findViewById<ImageButton>(R.id.imageButton95)



        backbuttton.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
        // Initialize date buttons
        dateButtons.addAll(listOf(
            findViewById(R.id.button60),
            findViewById(R.id.button61),
            findViewById(R.id.button62),
            findViewById(R.id.button63),
            findViewById(R.id.button64),
            findViewById(R.id.button65)
        ))

        // Set current dates for buttons
        setupDateButtons()

        // Set up other UI elements
        setupDreamContainers()

        // Set click listeners
        findViewById<ImageButton>(R.id.imageButton97).setOnClickListener {
            startActivity(Intent(this, DreamWriterActivity::class.java))
        }
    }

    private fun setupDateButtons() {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd EEE", Locale.getDefault())

        dateButtons.forEachIndexed { index, button ->
            // Set dates for the next 6 days
            calendar.add(Calendar.DATE, if (index == 0) 0 else 1)
            button.text = dateFormat.format(calendar.time)

            button.setOnClickListener {
                // Filter dreams by selected date
                val dreams = databaseHelper.getDreamsByDate(calendar.time)
                displayDreams(dreams)
            }
        }
    }

    private fun setupDreamContainers() {
        // Load all dreams initially
        val dreams = databaseHelper.getAllDreams()
        displayDreams(dreams)
    }

    private fun displayDreams(dreams: List<DreamEntry>) {
        // Clear all containers first
        dreamContainers.forEach { it.removeAllViews() }

        // Display up to 3 most recent dreams
        dreams.take(3).forEachIndexed { index, dream ->
            val container = dreamContainers[index]

            // Create dream card view
            val dreamView = layoutInflater.inflate(R.layout.dialog_add_log, container, false)

            // Set dream data
            dreamView.findViewById<TextView>(R.id.dreamTitle).text = dream.title
            dreamView.findViewById<TextView>(R.id.dreamDate).text =
                SimpleDateFormat("MMM dd", Locale.getDefault()).format(dream.date)
            dreamView.findViewById<TextView>(R.id.dreamPreview).text =
                dream.content.take(50) + if (dream.content.length > 50) "..." else ""

            // Highlight important dreams
            if (dream.isImportant) {
                dreamView.background = ContextCompat.getDrawable(this, R.drawable.coner10)
            } 


            // Set click listener
            dreamView.setOnClickListener {
                val intent = Intent(this, DreamDetailsActivity::class.java).apply {
                    putExtra("DREAM_ID", dream.id)
                }
                startActivity(intent)
            }

            container.addView(dreamView)
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh dreams when returning from other activities
        setupDreamContainers()
    }
}