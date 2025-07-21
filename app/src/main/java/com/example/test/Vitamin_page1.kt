package com.example.test

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Vitamin_page1 : AppCompatActivity() {

    private lateinit var sliderHandler: Handler
    private lateinit var sliderRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.vitamin_page1)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Banner Slider Setup
        val bannerViewPager = findViewById<ViewPager2>(R.id.bannerViewPager)
        val indicatorTabLayout = findViewById<TabLayout>(R.id.indicatorTabLayout)



        //Buttons
        val pregnant = findViewById<Button>(R.id.button27)
        val backbutton = findViewById<ImageButton>(R.id.imageButton62)
        val children = findViewById<Button>(R.id.button28)
        val adults  = findViewById<Button>(R.id.button29)

        val images = listOf(
            R.drawable.vitfgjdsjfg,
            R.drawable.vitfgjdsjfg2,
            R.drawable.vitfgjdsjfg3
        )

        bannerViewPager.adapter = BannerAdapter(images)

        // TabLayout indicator dots
        TabLayoutMediator(indicatorTabLayout, bannerViewPager) { _, _ -> }.attach()

        // Auto Slide Logic
        sliderHandler = Handler(Looper.getMainLooper())
        sliderRunnable = Runnable {
            bannerViewPager.currentItem = (bannerViewPager.currentItem + 1) % images.size
            sliderHandler.postDelayed(sliderRunnable, 3000)
        }

        sliderHandler.postDelayed(sliderRunnable, 3000)

        pregnant.setOnClickListener {
            val intent = Intent(this, Multi_SignIn::class.java) // Correct class name casing
            startActivity(intent)
        }
        children.setOnClickListener {
            val intent = Intent(this, Vitamin_page2::class.java) // Correct class name casing
            startActivity(intent)
        }
        adults.setOnClickListener {
            val intent = Intent(this, Vitamin_page2::class.java) // Correct class name casing
            startActivity(intent)
        }
        backbutton.setOnClickListener {
            val intent = Intent(this, Home::class.java) // Correct class name casing
            startActivity(intent)
        }

        // Reset timer on user interaction
        bannerViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, 3000)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        sliderHandler.removeCallbacks(sliderRunnable)
    }
}
