package vcmsa.ci.vibevault

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// this is saving everything as a private array
private val songTitles = ArrayList<String>()
private val artistNames = ArrayList<String>()
private val ratings = ArrayList<Int>()
private val comments = ArrayList<String>()

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        // Apply window insets for edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //this finds and reference UI elements
        val titleInput = findViewById<EditText>(R.id.inputSongTitle)
        val artistInput = findViewById<EditText>(R.id.inputArtistName)
        val ratingInput = findViewById<EditText>(R.id.inputRating)
        val commentInput = findViewById<EditText>(R.id.inputComment)

        val addBtn = findViewById<Button>(R.id.addSong)
        val viewBtn = findViewById<Button>(R.id.viewPlaylist)
        val exitBtn = findViewById<Button>(R.id.exitButton)

        addBtn.setOnClickListener {
            // Retrieve values inside the click listener
            val title = titleInput.text.toString().trim() // .trim() to remove leading/trailing spaces
            val artist = artistInput.text.toString().trim()
            val rating = ratingInput.text.toString().toIntOrNull() // Null if not a valid integer
            val comment = commentInput.text.toString().trim()

            // Validate inputs
            if (title.isEmpty() || artist.isEmpty() || comment.isEmpty() ||
                rating == null || rating !in (1..5)) { // Check if rating is null OR not in range
                Toast.makeText(this, "Please fill all fields correctly (rating 1-5)!", Toast.LENGTH_SHORT).show()
            } else {
                // Add validated data to the lists
                songTitles.add(title)
                artistNames.add(artist)
                ratings.add(rating) // Add the 'rating' variable, not the list itself
                comments.add(comment)

                Toast.makeText(this, "Song added!", Toast.LENGTH_SHORT).show()

                // Clear input fields
                titleInput.text.clear()
                artistInput.text.clear()
                ratingInput.text.clear()
                commentInput.text.clear()
            }
        }

        viewBtn.setOnClickListener {
            val intent = Intent(this, DetailedView::class.java).apply {
                putStringArrayListExtra("titles", songTitles)
                putStringArrayListExtra("artists", artistNames)
                putIntegerArrayListExtra("ratings", ratings)
                putStringArrayListExtra("comments", comments)
            }
            startActivity(intent)
        }

        exitBtn.setOnClickListener {
            finish() // Closes the current activity
        }
    }
}