package vcmsa.ci.vibevault // Ensure your package name is correct

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// Declare UI elements using lateinit var as in your example
private lateinit var allSongsTextView: TextView
private lateinit var averageRatingTextView: TextView
private lateinit var displaySongsButton: Button
private lateinit var calculateAverageButton: Button
private lateinit var returnToMainButton: Button

class DetailedView : AppCompatActivity() {

    // Lists to hold song data (can be nullable as they are populated from intent)
    private var songTitles: ArrayList<String>? = null
    private var artistNames: ArrayList<String>? = null
    private var ratings: ArrayList<Int>? = null
    private var comments: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detailed_view)

        // Apply window insets for edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize UI elements using findViewById
        allSongsTextView = findViewById(R.id.allSongsTextView)
        averageRatingTextView = findViewById(R.id.averageRatingTextView)
        displaySongsButton = findViewById(R.id.displaySongsButton)
        calculateAverageButton = findViewById(R.id.calculateAverageButton)
        returnToMainButton = findViewById(R.id.returnToMainButton)

        // Retrieve data passed from MainActivity2
        // Using ?: arrayListOf() to provide an empty list if null, similar to your example
        songTitles = intent.getStringArrayListExtra("titles") ?: arrayListOf()
        artistNames = intent.getStringArrayListExtra("artists") ?: arrayListOf()
        ratings = intent.getIntegerArrayListExtra("ratings") ?: arrayListOf()
        comments = intent.getStringArrayListExtra("comments") ?: arrayListOf()

        // Set OnClickListeners for buttons
        displaySongsButton.setOnClickListener {
            displaySongs()
        }

        calculateAverageButton.setOnClickListener {
            calculateAndDisplayAverageRating()
        }

        returnToMainButton.setOnClickListener {
            finish() // Closes the current activity and returns to the previous one (MainActivity2)
        }
    }

    // Function to display the list of songs using joinToString
    private fun displaySongs() {
        if (songTitles.isNullOrEmpty()) {
            allSongsTextView.text = "No songs added yet!"
            Toast.makeText(this, "No songs added yet!", Toast.LENGTH_SHORT).show()
            return
        }

        // Build the string for all songs using indices.joinToString
        val songListString = songTitles!!.indices.joinToString("\n\n") { i ->
            """
            Title: ${songTitles!![i]}
            Artist: ${artistNames!![i]}
            Rating: ${ratings!![i]}
            Comment: ${comments!![i]}
            """.trimIndent()
        }
        allSongsTextView.text = songListString
    }

    // Function to calculate and display the average rating
    private fun calculateAndDisplayAverageRating() {
        if (ratings.isNullOrEmpty() || ratings!!.isEmpty()) { // Check for both null and empty list
            Toast.makeText(this, "No ratings available to calculate average.", Toast.LENGTH_SHORT).show()
            averageRatingTextView.text = "Average Rating: N/A"
            return
        }

        var totalRating = 0.0
        // Accumulate total rating using a loop (or sum() function for brevity)
        // Using a loop as requested for 'calculate using a loop'
        for (rating in ratings!!) {
            totalRating += rating
        }

        // Calculate average rating
        val averageRating = totalRating / ratings!!.size

        // Display the average rating, formatted
        averageRatingTextView.text = String.format("Average Rating: %.2f", averageRating)
    }
}