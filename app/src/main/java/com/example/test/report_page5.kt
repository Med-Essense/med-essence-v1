package com.example.test

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.jvm.java

class report_page5 : AppCompatActivity() {

    private lateinit var symptomModel: Interpreter
    private lateinit var labelEncoderClasses: Array<String>
    private lateinit var symptomsList: List<String>
    private lateinit var df: List<DiseaseRecommendation>

    private lateinit var btnQuestionnaire: Button
    private lateinit var btnExit: Button
    private lateinit var btnSubmitSymptoms: Button
    private lateinit var btnBackToMain: Button
    private lateinit var questionnaireLayout: LinearLayout
    private lateinit var resultsLayout: LinearLayout
    private lateinit var symptomsContainer: LinearLayout
    private lateinit var tvCondition: TextView
    private lateinit var tvRecommendations: TextView
    private lateinit var tvRecommendationsTitle: TextView
    private lateinit var tvSimilarConditions: TextView

    data class DiseaseRecommendation(val disease: String, val recommendations: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.report_page5)

        // Load essentials
        symptomModel = Interpreter(FileUtil.loadMappedFile(this, "disease_model.tflite"))
        labelEncoderClasses = resources.openRawResource(R.raw.label_encoder_classes)
            .bufferedReader().use { it.readLines().toTypedArray() }

        df = resources.openRawResource(R.raw.disease_recommendations)
            .bufferedReader().useLines { lines ->
                lines.drop(1).mapNotNull {
                    val parts = it.split(",", limit = 2)
                    if (parts.size == 2) DiseaseRecommendation(parts[0].trim(), parts[1].trim())
                    else null
                }.toList()
            }

        symptomsList = listOf(
            "fever", "headache", "nausea", "vomiting", "fatigue",
            "joint_pain", "skin_rash", "cough", "weight_loss", "yellow_eyes"
        )

        // Setup UI
        btnQuestionnaire = findViewById(R.id.btnQuestionnaire)
        btnExit = findViewById(R.id.btnExit)
        btnSubmitSymptoms = findViewById(R.id.btnSubmitSymptoms)
        btnBackToMain = findViewById(R.id.btnBackToMain)
        questionnaireLayout = findViewById(R.id.questionnaireLayout)
        resultsLayout = findViewById(R.id.resultsLayout)
        symptomsContainer = findViewById(R.id.symptomsContainer)
        tvCondition = findViewById(R.id.tvCondition)
        tvRecommendations = findViewById(R.id.tvRecommendations)
        tvRecommendationsTitle = findViewById(R.id.tvRecommendationsTitle)
        tvSimilarConditions = findViewById(R.id.tvSimilarConditions)

        btnQuestionnaire.setOnClickListener { showQuestionnaire() }
        btnExit.setOnClickListener {
            val intent = Intent(this, report_page4::class.java)
            startActivity(intent)

        }
        btnSubmitSymptoms.setOnClickListener { submitSymptoms() }
        btnBackToMain.setOnClickListener { showMainMenu() }

        setupSymptomCheckboxes()
        showMainMenu()
    }

    private fun showMainMenu() {
        questionnaireLayout.visibility = View.GONE
        resultsLayout.visibility = View.GONE
    }

    private fun showQuestionnaire() {
        questionnaireLayout.visibility = View.VISIBLE
        resultsLayout.visibility = View.GONE
    }

    private fun setupSymptomCheckboxes() {
        symptomsContainer.removeAllViews()
        symptomsList.forEach { symptom ->
            val checkBox = CheckBox(this).apply {
                text = symptom.replace("_", " ").replaceFirstChar { it.uppercase() }
            }
            symptomsContainer.addView(checkBox)
        }
    }

    private fun submitSymptoms() {
        val input = FloatArray(symptomsList.size) { 0f }
        symptomsContainer.children.forEachIndexed { index, view ->
            if (view is CheckBox && view.isChecked) input[index] = 1f
        }

        val prediction = predictDisease(input)
        showResults(prediction)
    }

    private fun predictDisease(symptoms: FloatArray): String? {
        val inputBuffer = ByteBuffer.allocateDirect(symptoms.size * 4).order(ByteOrder.nativeOrder())
        symptoms.forEach { inputBuffer.putFloat(it) }

        val outputBuffer = ByteBuffer.allocateDirect(labelEncoderClasses.size * 4)
            .order(ByteOrder.nativeOrder())

        symptomModel.run(inputBuffer, outputBuffer)

        val outputArray = FloatArray(labelEncoderClasses.size).apply {
            outputBuffer.rewind()
            outputBuffer.asFloatBuffer().get(this)
        }

        val maxIndex = outputArray.indices.maxByOrNull { outputArray[it] } ?: return null
        return labelEncoderClasses.getOrNull(maxIndex)
    }

    private fun showResults(disease: String?) {
        questionnaireLayout.visibility = View.GONE
        resultsLayout.visibility = View.VISIBLE

        if (disease != null) {
            tvCondition.text = "Possible condition: ${disease.replaceFirstChar { it.uppercase() }}"
            val rec = df.firstOrNull { it.disease.equals(disease, ignoreCase = true) }?.recommendations
            tvRecommendations.text = rec ?: "No recommendations found."
            tvRecommendationsTitle.visibility = if (rec != null) View.VISIBLE else View.GONE
            tvSimilarConditions.visibility = View.GONE
        } else {
            tvCondition.text = "No condition could be determined"
            tvRecommendations.text = ""
            tvRecommendationsTitle.visibility = View.GONE
            tvSimilarConditions.visibility = View.GONE
        }
        val  backbutton  = findViewById<ImageButton>(R.id.imageButton105)



        backbutton.setOnClickListener {
            val intent = Intent(this, report_page4::class.java)
            startActivity(intent)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        symptomModel.close()
    }
}
