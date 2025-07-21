package com.example.test

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.imageview.ShapeableImageView

class  music_player : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var seekBar: SeekBar
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler
    private var pause = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.music_player)

        val songNameTV = findViewById<TextView>(R.id.songNamePA)
        val songImg = findViewById<ShapeableImageView>(R.id.songImgPA2)
        val playPauseBtn = findViewById<ImageButton>(R.id.playPauseBtnPA)
        val backBtn = findViewById<ImageButton>(R.id.backBtnPA)
        seekBar = findViewById(R.id.seekBarPA)
        val tvSeekBarStart = findViewById<TextView>(R.id.tvSeekBarStart)
        val tvSeekBarEnd = findViewById<TextView>(R.id.tvSeekBarEnd)

        val songName = intent.getStringExtra("SONG_NAME")
        val songResource = intent.getIntExtra("SONG_RESOURCE", 0)
        val imageResource = intent.getIntExtra("IMAGE_RESOURCE", 0)

        if (songResource == 0) {
            finish()
            return
        }

        songNameTV.text = songName ?: "No Title"
        songImg.setImageResource(imageResource)

        mediaPlayer = MediaPlayer.create(this, songResource)
        mediaPlayer.start()
        playPauseBtn.setImageResource(android.R.drawable.ic_media_pause)

        handler = Handler()

        val duration = mediaPlayer.duration / 1000
        val minutes = duration / 60
        val seconds = duration % 60
        tvSeekBarEnd.text = String.format("%d:%02d", minutes, seconds)

        seekBar.max = mediaPlayer.duration
        runnable = object : Runnable {
            override fun run() {
                seekBar.progress = mediaPlayer.currentPosition

                val currentPos = mediaPlayer.currentPosition / 1000
                val currentMin = currentPos / 60
                val currentSec = currentPos % 60
                tvSeekBarStart.text = String.format("%d:%02d", currentMin, currentSec)

                handler.postDelayed(this, 1000)
            }
        }
        handler.postDelayed(runnable, 0)

        playPauseBtn.setOnClickListener {
            if (pause) {
                mediaPlayer.start()
                playPauseBtn.setImageResource(android.R.drawable.ic_media_pause)
                pause = false
            } else {
                mediaPlayer.pause()
                playPauseBtn.setImageResource(android.R.drawable.ic_media_play)
                pause = true
            }
        }

        backBtn.setOnClickListener {
            mediaPlayer.stop()
            finish()
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(sb: SeekBar?) {}
            override fun onStopTrackingTouch(sb: SeekBar?) {}
        })

        mediaPlayer.setOnCompletionListener {
            playPauseBtn.setImageResource(android.R.drawable.ic_media_play)
            seekBar.progress = 0
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        handler.removeCallbacks(runnable)
    }
}
