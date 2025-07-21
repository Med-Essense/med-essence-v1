package com.example.test

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.test.database.DreamDatabaseHelper
import java.util.Locale

class dialog_add_log : AppCompatActivity() {

    private lateinit var dbHelper: DreamDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_log)

        dbHelper = DreamDatabaseHelper(this)
        val dreams = dbHelper.getAllDreams()
        val container = findViewById<LinearLayout>(R.id.dreamContainer) // must exist in your XML

        dreams.forEach { dream ->
            val view = layoutInflater.inflate(R.layout.dialog_add_log, container, false)

            view.findViewById<TextView>(R.id.dreamTitle).text = dream.title
            view.findViewById<TextView>(R.id.dreamContent).text = dream.content
            view.findViewById<TextView>(R.id.dreamCategory).text = dream.category


            container.addView(view)
        }
    }
}
