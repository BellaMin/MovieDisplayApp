package com.example.moviedisplay

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.style.TtsSpan
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import org.w3c.dom.Text

class MainActivity2 : AppCompatActivity() {

    private var SELECTED_MOVIE_INFO = "selected_movie_info"
    private var Database = "local_database"
    private lateinit var title: TextView
    private var dbHelper : Database = Database(this)
    private lateinit var id:TextView
    private lateinit var description: TextView
    private lateinit var rating: TextView
    private lateinit var releasedDate : TextView
    private lateinit var add:Button
    private lateinit var back:Button
    private lateinit var poster: ImageView
    private lateinit var backdrop: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val intent = getIntent()

        title = findViewById(R.id.title)
        id = findViewById(R.id.id)
        description = findViewById(R.id.description)
        rating = findViewById(R.id.rating)
        releasedDate = findViewById(R.id.releasedDate)
        add = findViewById(R.id.favorite)
        back = findViewById(R.id.Back)
        poster = findViewById(R.id.imageView3)
        backdrop = findViewById(R.id.imageView2)


        val data: MovieInfo? = intent.getSerializableExtra(SELECTED_MOVIE_INFO) as MovieInfo?

        if (data != null) {
            title.text = data.mTittle
            id.text = data.mID.toString()
            description.text = data.mDescription
            rating.text = data.mRating.toString()
            releasedDate.text = data.mReleaseD

            /**
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342${data.mPoster}")
                .transform(CenterCrop())
                .into(poster)

            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342${data.mBD}")
                .transform(CenterCrop())
                .into(backdrop)

            **/

        }
        else{

            Toast.makeText(this, "Error, Please Retry", Toast.LENGTH_LONG).show()
            title.text = "Error"
            id.text = "Error"
            description.text ="Error"
            rating.text = "Error"
            releasedDate.text = "Error"

        }



        add.setOnClickListener {
            if(data != null){
                val res = dbHelper.allData
                Toast.makeText(this, "" + res.count, Toast.LENGTH_LONG).show()
            }
        }

        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
            }
            startActivity(intent)
        }


    }
}