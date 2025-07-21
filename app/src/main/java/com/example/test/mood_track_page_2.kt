package com.example.test

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.jvm.java

class mood_track_page_2 : AppCompatActivity() {

    private lateinit var happyLayout: ConstraintLayout
    private lateinit var sadLayout: ConstraintLayout
    private lateinit var neutralLayout: ConstraintLayout
    private lateinit var seekBar: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mood_track_page_2)

        // Initialize views
        happyLayout = findViewById(R.id.happyLayout)
        sadLayout = findViewById(R.id.sadLayout)
        neutralLayout = findViewById(R.id.neutralLayout)
        seekBar = findViewById(R.id.seekBar)

        // Set initial visibility
        happyLayout.visibility = ConstraintLayout.VISIBLE
        sadLayout.visibility = ConstraintLayout.GONE
        neutralLayout.visibility = ConstraintLayout.GONE

        val backbatton = findViewById<ImageButton>(R.id.imageButton69)

        backbatton.setOnClickListener {
            val intent = Intent(this, mood_track_page_1::class.java)
            startActivity(intent)
        }
        // SeekBar listener for mood selection
        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                when (progress) {
                    0 -> {
                        happyLayout.visibility = ConstraintLayout.VISIBLE
                        sadLayout.visibility = ConstraintLayout.GONE
                        neutralLayout.visibility = ConstraintLayout.GONE
                    }
                    1 -> {
                        happyLayout.visibility = ConstraintLayout.GONE
                        sadLayout.visibility = ConstraintLayout.VISIBLE
                        neutralLayout.visibility = ConstraintLayout.GONE
                    }
                    2 -> {
                        happyLayout.visibility = ConstraintLayout.GONE
                        sadLayout.visibility = ConstraintLayout.GONE
                        neutralLayout.visibility = ConstraintLayout.VISIBLE
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        // Set click listeners for happy songs
        findViewById<ImageButton>(R.id.imageButton66).setOnClickListener {
            startMusicPlayer("Happy Song 1", R.raw.sad_song3, R.drawable.frame_48095516)
        }
        findViewById<ImageButton>(R.id.imageButton67).setOnClickListener {
            startMusicPlayer("Happy Song 2", R.raw.sad_song3, R.drawable.frame_48095515)
        }
        findViewById<ImageButton>(R.id.imageButton68).setOnClickListener {
            startMusicPlayer("Happy Song 3", R.raw.sad_song3, R.drawable.frame_48095514)
        }

        // Set click listeners for sad songs
        findViewById<ImageButton>(R.id.imageButton662).setOnClickListener {
            startMusicPlayer("Sad Song 1", R.raw.sad_song3, R.drawable.frame_48095516)
        }
        findViewById<ImageButton>(R.id.imageButton672).setOnClickListener {
            startMusicPlayer("Sad Song 2", R.raw.sad_song3, R.drawable.frame_48095515)
        }
        findViewById<ImageButton>(R.id.imageButton682).setOnClickListener {
            startMusicPlayer("Sad Song 3", R.raw.sad_song3, R.drawable.frame_48095514)
        }

        // Set click listeners for neutral songs
        findViewById<ImageButton>(R.id.imageButton663).setOnClickListener {
            startMusicPlayer("Neutral Song 1", R.raw.sad_song3, R.drawable.frame_48095516)
        }
        findViewById<ImageButton>(R.id.imageButton673).setOnClickListener {
            startMusicPlayer("Neutral Song 2", R.raw.sad_song3, R.drawable.frame_48095515)
        }
        findViewById<ImageButton>(R.id.imageButton683).setOnClickListener {
            startMusicPlayer("Neutral Song 3", R.raw.sad_song3, R.drawable.frame_48095514)
        }
    }

    private fun startMusicPlayer(songName: String, songResource: Int, imageResource: Int) {
        val intent = Intent(this, music_player::class.java).apply {
            putExtra("SONG_NAME", songName)
            putExtra("SONG_RESOURCE", songResource)
            putExtra("IMAGE_RESOURCE", imageResource)
        }
        startActivity(intent)
    }
}