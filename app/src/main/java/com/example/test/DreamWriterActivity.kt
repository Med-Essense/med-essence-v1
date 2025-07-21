package com.example.test


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.test.database.DreamDatabaseHelper
import com.example.test.model.DreamEntry
import java.util.*

class DreamWriterActivity : AppCompatActivity() {

    private lateinit var etTitle: EditText
    private lateinit var etContent: EditText
    private lateinit var spinner: Spinner
    private lateinit var backButton: ImageButton
    private lateinit var saveButton: ImageButton
    private lateinit var saveButtonBottom: Button
    private lateinit var boldButton: Button
    private lateinit var italicButton: Button
    private lateinit var underlineButton: Button
    private lateinit var textColorButton: Button
    private lateinit var textSizeButton: Button
    private lateinit var dbHelper: DreamDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_dream_log)

        dbHelper = DreamDatabaseHelper(this)

        // Initialize views
        etTitle = findViewById(R.id.etTitle)
        etContent = findViewById(R.id.etContent)
        spinner = findViewById(R.id.spinner)
        backButton = findViewById(R.id.imageButton99)
        saveButton = findViewById(R.id.imageButton100)
        saveButtonBottom = findViewById(R.id.button74)
        boldButton = findViewById(R.id.button58)
        italicButton = findViewById(R.id.button59)
        underlineButton = findViewById(R.id.button71)
        textColorButton = findViewById(R.id.button72)
        textSizeButton = findViewById(R.id.button73)

        // Set up button click listeners
        backButton.setOnClickListener {
            finish()
        }

        saveButton.setOnClickListener {
            saveDreamEntry()
        }

        saveButtonBottom.setOnClickListener {
            saveDreamEntry()
        }

        // Text formatting buttons
        boldButton.setOnClickListener {
            applyStyle(StyleSpan(android.graphics.Typeface.BOLD))
        }

        italicButton.setOnClickListener {
            applyStyle(StyleSpan(android.graphics.Typeface.ITALIC))
        }

        underlineButton.setOnClickListener {
            applyStyle(UnderlineSpan())
        }

        textColorButton.setOnClickListener {
            Toast.makeText(this, "Text color change will be implemented", Toast.LENGTH_SHORT).show()
        }

        textSizeButton.setOnClickListener {
            Toast.makeText(this, "Text size change will be implemented", Toast.LENGTH_SHORT).show()
        }

        // Set up spinner with categories
        setupSpinner()
    }

    private fun setupSpinner() {
        val categories = arrayOf(
            "General",
            "Important",
            "Nightmare",
            "Lucid",
            "Recurring"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            categories
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        spinner.adapter = adapter
    }

    private fun saveDreamEntry() {
        val title = etTitle.text.toString()
        val content = etContent.text.toString()
        val category = spinner.selectedItem.toString()

        if (title.isNotEmpty() && content.isNotEmpty()) {
            val dreamEntry = DreamEntry(
                title = title,
                content = content,
                category = category,
                date = Date(),
                isImportant = category == "Important",

                )

            // Save to database
            dbHelper.addDreamEntry(dreamEntry)

            Toast.makeText(this, "Dream saved successfully", Toast.LENGTH_SHORT).show()
            finish() // Close the activity instead of starting a new one
        } else {
            if (title.isEmpty()) etTitle.error = "Title required"
            if (content.isEmpty()) etContent.error = "Content required"
        }
    }

    private fun applyStyle(span: Any) {
        val selectionStart = etContent.selectionStart
        val selectionEnd = etContent.selectionEnd

        if (selectionStart > selectionEnd) return

        val content = SpannableString(etContent.text)
        content.setSpan(span, selectionStart, selectionEnd, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        etContent.text = Editable.Factory.getInstance().newEditable(content)
    }
}