package com.example.test


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ImageButton
import kotlin.jvm.java

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.homepage)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Button navigation setup

        val buttonXray = findViewById<ImageButton>(R.id.button8)
        val reportd = findViewById<ImageButton>(R.id.c)
        val vitaminchecker = findViewById<ImageButton>(R.id.button9)
        val buttonQa = findViewById<ImageButton>(R.id.button10)
        val buttonaicamera = findViewById<ImageButton>(R.id.imageButton74)
        val map1 = findViewById<ImageButton>(R.id.imageButton19)
        val buttonproduct = findViewById<ImageButton>(R.id.imageButton6)
        val profile = findViewById<ImageButton>(R.id.imageButton20)
        val sheduler = findViewById<ImageButton>(R.id.imageView40)

        val postsurgeryrecovery = findViewById<Button>(R.id.button34)
        val crash = findViewById<Button>(R.id.button36)
        val moodtracker = findViewById<Button>(R.id.button37)






        buttonXray.setOnClickListener {
            val intent = Intent(this, X_RayComparison_page1 ::class.java)
            startActivity(intent)
        }

        vitaminchecker.setOnClickListener {
            val intent = Intent(this, Vitamin_page1::class.java)
            startActivity(intent)
        }

        buttonQa.setOnClickListener {
            val intent = Intent(this, QandApage_1::class.java)
            startActivity(intent)
        }

        buttonaicamera.setOnClickListener {
            val intent = Intent(this, fake_medicine_defector_page01::class.java)
            startActivity(intent)
        }
        buttonproduct.setOnClickListener {
            val intent = Intent(this, ai_gesstion_page1::class.java)
            startActivity(intent)
        }
        map1.setOnClickListener {
            val intent = Intent(this, map::class.java)
            startActivity(intent)
        }
        profile.setOnClickListener {
            val intent = Intent(this, User_Profile::class.java)
            startActivity(intent)
        }
        postsurgeryrecovery.setOnClickListener {
            val intent = Intent(this, post_surgery_recovery_page1::class.java)
            startActivity(intent)
        }
        crash.setOnClickListener {
            val intent = Intent(this, crash1::class.java)
            startActivity(intent)
        }
        moodtracker.setOnClickListener {
            val intent = Intent(this, mood_track_page_1::class.java)
            startActivity(intent)
        }
        sheduler.setOnClickListener {
            val intent = Intent(this, m_scheduler_1::class.java)
            startActivity(intent)
        }
        reportd.setOnClickListener {
            val intent = Intent(this, report_page1::class.java)
            startActivity(intent)
        }




    }
}
