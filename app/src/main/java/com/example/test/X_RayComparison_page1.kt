package com.example.test

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import java.io.IOException

class X_RayComparison_page1 : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var resultTextView: TextView
    private lateinit var classifyButton: ImageButton // Changed from Button to ImageButton
    private lateinit var selectImageButton: ImageButton // Changed from Button to ImageButton
    private lateinit var backbutton: ImageButton
    private lateinit var progressBar: ProgressBar

    private var selectedBitmap: Bitmap? = null
    private val customLabels = listOf("X-ray Normal level", "X-ray Risk Level", "Recovery Percentage")
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.x_ray_comparison_page1)

        imageView = findViewById(R.id.imageView)
        resultTextView = findViewById(R.id.resultTextView)
        classifyButton = findViewById(R.id.imageButton51) // Make sure this matches your XML
        selectImageButton = findViewById(R.id.imageView67)
        backbutton = findViewById(R.id.imageView42)// Make sure this matches your XML
        // Add this to your XML if not present

        selectImageButton.setOnClickListener {
            openImageChooser()
        }

        backbutton.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
        classifyButton.setOnClickListener {
            selectedBitmap?.let { bitmap ->
                analyzeImage(bitmap)
            } ?: run {
                Toast.makeText(this, "Please select an image first", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            try {
                val imageUri: Uri? = data.data
                imageUri?.let {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, it)
                    selectedBitmap = bitmap
                    imageView.setImageBitmap(bitmap)
                    resultTextView.text = "Image selected. Click 'Analyze X-ray' to proceed."
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun analyzeImage(bitmap: Bitmap) {
        progressBar.visibility = ProgressBar.VISIBLE
        classifyButton.isEnabled = false

        try {
            Thread {
                try {
                    val options = ImageClassifier.ImageClassifierOptions.builder()
                        .setMaxResults(3)
                        .build()

                    val model = ImageClassifier.createFromFileAndOptions(
                        this,
                        "mobilenet_v2.tflite", // Make sure this model file exists in assets
                        options
                    )

                    val imageProcessor = ImageProcessor.Builder()
                        .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
                        .add(NormalizeOp(127.5f, 127.5f))
                        .build()

                    val tensorImage = TensorImage(DataType.FLOAT32)
                    tensorImage.load(bitmap)
                    val processedImage = imageProcessor.process(tensorImage)

                    val results = model.classify(processedImage)

                    runOnUiThread {
                        displayResults(results)
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                        e.printStackTrace()
                    }
                } finally {
                    runOnUiThread {
                        progressBar.visibility = ProgressBar.GONE
                        classifyButton.isEnabled = true
                    }
                }
            }.start()
        } catch (e: Exception) {
            progressBar.visibility = ProgressBar.GONE
            classifyButton.isEnabled = true
            Toast.makeText(this, "Error initializing classifier", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    private fun displayResults(results: List<Classifications>) {
        val stringBuilder = StringBuilder()
        stringBuilder.append("Analysis Results:\n\n")

        if (results.isEmpty()) {
            stringBuilder.append("No results found")
        } else {
            val categories = results[0].categories
            for ((index, category) in categories.withIndex()) {
                val label = if (index < customLabels.size) {
                    customLabels[index]
                } else {
                    category.label ?: "Other"
                }
                stringBuilder.append("${index + 1}. $label: ${"%.2f".format(category.score * 100)}%\n")
            }
        }

        resultTextView.text = stringBuilder.toString()
    }
}