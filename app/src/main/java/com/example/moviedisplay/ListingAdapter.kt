package com.example.moviedisplay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import org.w3c.dom.Text

class ListingAdapter(
    private var movies: MutableList<MovieInfo>,
    private val cellClickListener: CellClickListener
) : RecyclerView.Adapter<ListingAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.movie_detail, parent, false)
        return MovieViewHolder(view)
    }

    fun appendMovies(movies: List<MovieInfo>) {
        this.movies.addAll(movies)
        notifyItemRangeInserted(
            this.movies.size,
            movies.size - 1
        )
    }



    override fun getItemCount(): Int = movies.size



    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])

        val currentData = movies[position]

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(currentData)
        }

    }

    fun updateMovies(movies: MutableList<MovieInfo>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.movie_poster)
        private val rating:TextView = itemView.findViewById(R.id.rating)
        private val releasedDate:TextView = itemView.findViewById(R.id.releasedDate)
        private val title:TextView = itemView.findViewById(R.id.title)

        fun bind(movie: MovieInfo) {

            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${movie.mPoster}")
                .transform(CenterCrop())
                .into(poster)

            title.text = movie.mTittle
            rating.text = movie.mRating.toString()
            releasedDate.text = movie.mReleaseD



        }
    }
}