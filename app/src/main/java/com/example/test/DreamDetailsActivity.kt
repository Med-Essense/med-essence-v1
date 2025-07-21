package com.example.test

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.test.database.DreamDatabaseHelper
import java.text.SimpleDateFormat
import java.util.*

class DreamDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_log)

        val dreamId = intent.getLongExtra("DREAM_ID", -1)
        if (dreamId != -1L) {
            val dream = DreamDatabaseHelper(this).getDreamById(dreamId)
            dream?.let {
                findViewById<TextView>(R.id.dreamTitle).text = it.title
                findViewById<TextView>(R.id.dreamContent).text = it.content
                findViewById<TextView>(R.id.dreamCategory).text = it.category

                // Format the date using SimpleDateFormat
                val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                findViewById<TextView>(R.id.dreamDate).text = dateFormat.format(it.date)
            }
        }
    }
}