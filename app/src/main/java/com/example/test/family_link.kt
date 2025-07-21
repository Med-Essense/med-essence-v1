package com.example.test

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.util.UUID

class family_link : AppCompatActivity() {

    private lateinit var qrImageView: ImageView
    private lateinit var userIdTextView: TextView
    private lateinit var scanButton: ImageButton
    private var user1Id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.family_link)

        qrImageView = findViewById(R.id.Qrcode)
        userIdTextView = findViewById(R.id.userIdTextView)
        scanButton = findViewById(R.id.imageButton4)

        // Load or generate QR automatically
        loadOrGenerateUserId()

        scanButton.setOnClickListener {
            startActivity(Intent(this, ScanActivity::class.java))
        }
    }

    private fun loadOrGenerateUserId() {
        val sharedPref = getSharedPreferences("QR_PREFS", Context.MODE_PRIVATE)
        user1Id = sharedPref.getString("USER1_ID", "") ?: ""

        // If no ID, generate and save one
        if (user1Id.isEmpty()) {
            user1Id = UUID.randomUUID().toString()
            sharedPref.edit().putString("USER1_ID", user1Id).apply()
        }

        generateQRCode(user1Id)
    }

    private fun generateQRCode(data: String) {
        try {
            val bitMatrix: BitMatrix = MultiFormatWriter().encode(
                data, BarcodeFormat.QR_CODE, 500, 500
            )
            val bitmap: Bitmap = BarcodeEncoder().createBitmap(bitMatrix)
            qrImageView.setImageBitmap(bitmap)
            userIdTextView.text = "User1 ID:\n$data"
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}