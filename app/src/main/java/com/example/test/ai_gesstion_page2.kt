package com.example.test

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.image.ops.ResizeWithCropOrPadOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class ai_gesstion_page2 : AppCompatActivity() {
    private lateinit var interpreter: Interpreter
    private lateinit var labels: List<String>
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var btnAttach: ImageView
    private lateinit var btnCamera: ImageView
    private lateinit var btnSend: ImageView
    private lateinit var etMessage: EditText
    private lateinit var backbutton: ImageView

    private val animalInfo = mapOf(
        "antelope" to "🦌 Antelope: Fast-running herbivore with horns.",
        "badger" to "🦡 Badger: Short-legged omnivore with distinctive markings.",
        "bat" to "🦇 Bat: Only flying mammal.",
        "bear" to "🐻 Bear: Large mammal with thick fur and strong limbs.",
        "bee" to "🐝 Bee: Flying insect that produces honey.",
        "beetle" to "🪲 Beetle: Insect with hard protective forewings.",
        "bison" to "🦬 Bison: Large bovine with shaggy mane.",
        "boar" to "🐗 Boar: Wild pig with tusks.",
        "butterfly" to "🦋 Butterfly: Flying insect with colorful wings.",
        "cat" to "🐱 Cat: Small domesticated carnivorous mammal.",
        "caterpillar" to "🐛 Caterpillar: Larval stage of butterflies and moths.",
        "chimpanzee" to "🐵 Chimpanzee: Great ape with high intelligence.",
        "cockroach" to "🪳 Cockroach: Resilient insect that can survive in many environments.",
        "cow" to "🐮 Cow: Domesticated cattle raised for milk and meat.",
        "coyote" to "🐺 Coyote: Canine native to North America.",
        "crab" to "🦀 Crab: Decapod crustacean with thick exoskeleton.",
        "crow" to "🐦⬛ Crow: Highly intelligent black bird.",
        "deer" to "🦌 Deer: Hoofed grazing animal with antlers (in males).",
        "dog" to "🐶 Dog: Domesticated descendant of wolves.",
        "dolphin" to "🐬 Dolphin: Highly intelligent marine mammal.",
        "donkey" to "🐴 Donkey: Domesticated member of the horse family.",
        "dragonfly" to "🪰 Dragonfly: Flying insect with long body and large eyes.",
        "duck" to "🦆 Duck: Waterfowl with broad bill and webbed feet.",
        "eagle" to "🦅 Eagle: Large bird of prey with powerful vision.",
        "elephant" to "🐘 Elephant: Largest land animal with trunk and tusks.",
        "flamingo" to "🦩 Flamingo: Wading bird with pink plumage.",
        "fly" to "🪰 Fly: Flying insect with compound eyes.",
        "fox" to "🦊 Fox: Small canine with bushy tail.",
        "goat" to "🐐 Goat: Horned ruminant often domesticated.",
        "goldfish" to "🐟 Goldfish: Small freshwater fish, often kept as pets.",
        "goose" to "🦢 Goose: Waterfowl larger than ducks.",
        "gorilla" to "🦍 Gorilla: Largest living primate.",
        "grasshopper" to "🦗 Grasshopper: Herbivorous insect with powerful hind legs.",
        "hamster" to "🐹 Hamster: Small rodent often kept as pet.",
        "hare" to "🐇 Hare: Fast-running mammal similar to rabbit.",
        "hedgehog" to "🦔 Hedgehog: Small spiny mammal.",
        "hippopotamus" to "🦛 Hippopotamus: Large semi-aquatic mammal.",
        "hornbill" to "🦜 Hornbill: Tropical bird with large curved bill.",
        "horse" to "🐴 Horse: Large domesticated hoofed mammal.",
        "hummingbird" to "🐦 Hummingbird: Small bird capable of hovering flight.",
        "hyena" to "🐾 Hyena: Carnivorous mammal with distinctive laugh-like call.",
        "jellyfish" to "🎐 Jellyfish: Free-swimming marine animal with umbrella-shaped bell.",
        "kangaroo" to "🦘 Kangaroo: Marsupial with powerful hind legs.",
        "koala" to "🐨 Koala: Arboreal herbivorous marsupial.",
        "ladybugs" to "🐞 Ladybug: Small beetle with spotted elytra.",
        "leopard" to "🐆 Leopard: Large cat with rosette-patterned fur.",
        "lion" to "🦁 Lion: Large social cat, king of the jungle.",
        "lizard" to "🦎 Lizard: Reptile with elongated body and tail.",
        "lobster" to "🦞 Lobster: Large marine crustacean with claws.",
        "mosquito" to "🦟 Mosquito: Flying insect that feeds on blood.",
        "moth" to "🦋 Moth: Nocturnal flying insect similar to butterfly.",
        "mouse" to "🐭 Mouse: Small rodent with pointed snout.",
        "octopus" to "🐙 Octopus: Eight-limbed mollusk with soft body.",
        "okapi" to "🦓 Okapi: Forest-dwelling relative of giraffe.",
        "orangutan" to "🦧 Orangutan: Large arboreal great ape.",
        "otter" to "🦦 Otter: Playful aquatic mammal.",
        "owl" to "🦉 Owl: Nocturnal bird of prey with large eyes.",
        "ox" to "🐂 Ox: Domesticated bovine used as draft animal.",
        "oyster" to "🦪 Oyster: Bivalve mollusk that produces pearls.",
        "panda" to "🐼 Panda: Bear with distinctive black-and-white markings.",
        "parrot" to "🦜 Parrot: Colorful bird capable of mimicking speech.",
        "pelecaniformes" to "🦢 Pelican: Large water bird with throat pouch.",
        "penguin" to "🐧 Penguin: Flightless bird adapted to aquatic life.",
        "pig" to "🐷 Pig: Domesticated omnivorous hoofed mammal.",
        "pigeon" to "🕊 Pigeon: Plump bird common in urban areas.",
        "porcupine" to "🦔 Porcupine: Rodent with sharp quills.",
        "possum" to "🦡 Possum: Marsupial native to Australasia and Americas.",
        "raccoon" to "🦝 Raccoon: Nocturnal mammal with facial mask.",
        "rat" to "🐀 Rat: Medium-sized rodent with long tail.",
        "reindeer" to "🦌 Reindeer: Deer adapted to Arctic regions.",
        "rhinoceros" to "🦏 Rhinoceros: Large herbivore with horn(s) on snout.",
        "sandpiper" to "🦆 Sandpiper: Small wading bird.",
        "seahorse" to "🦕 Seahorse: Small fish with horse-like head.",
        "seal" to "🦭 Seal: Semi-aquatic marine mammal.",
        "shark" to "🦈 Shark: Cartilaginous fish with multiple rows of teeth.",
        "sheep" to "🐑 Sheep: Domesticated ruminant raised for wool.",
        "snake" to "🐍 Snake: Legless reptile with elongated body.",
        "sparrow" to "🐦 Sparrow: Small passerine bird.",
        "squid" to "🦑 Squid: Cephalopod with elongated body and tentacles.",
        "squirrel" to "🐿 Squirrel: Small tree-dwelling rodent with bushy tail.",
        "starfish" to "⭐ Starfish: Marine invertebrate with radial symmetry.",
        "swan" to "🦢 Swan: Large waterfowl with long neck.",
        "tiger" to "🐯 Tiger: Largest cat species with distinctive stripes.",
        "turkey" to "🦃 Turkey: Large bird native to North America.",
        "turtle" to "🐢 Turtle: Reptile with bony or cartilaginous shell.",
        "whale" to "🐋 Whale: Very large marine mammal.",
        "wolf" to "🐺 Wolf: Largest member of the dog family.",
        "wombat" to "🦥 Wombat: Short-legged, muscular marsupial.",
        "woodpecker" to "🦜 Woodpecker: Bird that pecks at tree bark.",
        "zebra" to "🦓 Zebra: African equids with black-and-white stripes."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        try {
            initModel()
        } catch (e: Exception) {
            showError("Failed to initialize model: ${e.message}")
            return
        }
    }

    private fun initViews() {
        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        btnAttach = findViewById(R.id.btnAttach)
        btnCamera = findViewById(R.id.btnCamera)
        btnSend = findViewById(R.id.btnSend)
        etMessage = findViewById(R.id.etMessage)
        backbutton = findViewById(R.id.imageba)

        // Setup RecyclerView
        chatAdapter = ChatAdapter(mutableListOf())
        chatRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ai_gesstion_page2)
            adapter = chatAdapter
        }
        backbutton.setOnClickListener {
            val intent = Intent(this, ai_gesstion_page1::class.java)
            startActivity(intent)
        }
        // Add welcome message
        addMessage("Bot", "Hi! Send me a photo of an animal and I'll tell you what it is!", false)

        btnAttach.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
                type = "image/*"
            }
            startActivityForResult(intent, PICK_IMAGE)
        }

        btnCamera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAPTURE_IMAGE)
        }

        btnSend.setOnClickListener {
            val message = etMessage.text.toString()
            if (message.isNotEmpty()) {
                addMessage("You", message, true)
                etMessage.text.clear()
                // Scroll to bottom
                chatRecyclerView.smoothScrollToPosition(chatAdapter.itemCount - 1)
            }
        }
    }

    private fun initModel() {
        try {
            interpreter = Interpreter(loadModelFile("animal_model.tflite"))
            labels = FileUtil.loadLabels(this, "labels.txt")
            Log.d("ModelInit", "Model and labels loaded successfully")
        } catch (e: Exception) {
            Log.e("ModelInit", "Error initializing model", e)
            throw e
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_IMAGE -> {
                    data?.data?.let { uri ->
                        processImageFromUri(uri)
                    }
                }
                CAPTURE_IMAGE -> {
                    val bitmap = data?.extras?.get("data") as? Bitmap
                    bitmap?.let {
                        processImageBitmap(it)
                    }
                }
            }
        }
    }

    private fun processImageFromUri(uri: Uri) {
        try {
            contentResolver.openInputStream(uri)?.use { stream ->
                val bitmap = BitmapFactory.decodeStream(stream)
                processImageBitmap(bitmap)
            }
        } catch (e: Exception) {
            showError("Failed to process image")
            Log.e("ImageProcessing", "Error processing image", e)
        }
    }

    private fun processImageBitmap(bitmap: Bitmap) {
        // Add image to chat
        addMessage("You", "", true, bitmap)

        try {
            val (label, confidence) = classifyImage(bitmap)
            val displayLabel = label.replace("_", " ").capitalize()
            val info = animalInfo[label.toLowerCase()] ?: "No additional information available."

            val response = "Detected: $displayLabel (${"%.2f".format(confidence * 100)}%)\n\n$info"
            addMessage("Bot", response, false)

            // Scroll to bottom
            chatRecyclerView.smoothScrollToPosition(chatAdapter.itemCount - 1)
        } catch (e: Exception) {
            showError("Failed to classify image")
            Log.e("Classification", "Error classifying image", e)
        }
    }

    private fun classifyImage(bitmap: Bitmap): Pair<String, Float> {
        if (!this::labels.isInitialized) {
            throw IllegalStateException("Labels not initialized")
        }

        // Create ImageProcessor with proper preprocessing
        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeWithCropOrPadOp(224, 224)) // First crop then resize
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
            .build()

        // Convert bitmap to TensorImage
        val tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(bitmap)

        // Process the image
        val processedImage = imageProcessor.process(tensorImage)

        // Create output buffer
        val outputBuffer = TensorBuffer.createFixedSize(intArrayOf(1, labels.size), DataType.FLOAT32)

        // Run inference
        interpreter.run(processedImage.buffer, outputBuffer.buffer.rewind())
        val outputs = outputBuffer.floatArray

        // Get prediction
        val maxIdx = outputs.indices.maxByOrNull { outputs[it] } ?: -1
        if (maxIdx == -1 || maxIdx >= labels.size) {
            throw IllegalStateException("Invalid prediction index")
        }

        // Get the confidence score and convert to percentage
        val confidence = outputs[maxIdx]

        return labels[maxIdx] to confidence
    }
    private fun addMessage(sender: String, message: String, isUser: Boolean, image: Bitmap? = null) {
        val chatMessage = ChatMessage(sender, message, isUser, image)
        chatAdapter.addMessage(chatMessage)
    }

    private fun loadModelFile(modelName: String): MappedByteBuffer {
        return FileUtil.loadMappedFile(this, modelName)
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        addMessage("Bot", "Error: $message", false)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::interpreter.isInitialized) {
            interpreter.close()
        }
    }

    companion object {
        private const val PICK_IMAGE = 100
        private const val CAPTURE_IMAGE = 101
    }
}