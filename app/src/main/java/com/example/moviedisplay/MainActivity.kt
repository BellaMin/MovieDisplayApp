package com.example.moviedisplay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() , CellClickListener{

    private lateinit var Rview: RecyclerView
    private lateinit var Upview: RecyclerView
    private lateinit var adapter: ListingAdapter
    private lateinit var LayoutManger: LinearLayoutManager
    private var SELECTED_MOVIE_INFO = "selected_movie_info"
    val scope = CoroutineScope(Job() + Dispatchers.Main)
    internal var dbHelper = Database(this)


    private var CurrentPageNo = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Rview = findViewById(R.id.movies)
        Upview = findViewById(R.id.upComing)
        LayoutManger = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        Rview.layoutManager = LayoutManger;
        adapter = ListingAdapter(mutableListOf(),this)
        Rview.adapter = adapter

        if(getCount() == 0){
            scope.launch {
                // New coroutine that can call suspend functions
                storePopularMovies()
                storeUpComingMovies()
            }

            getPopularMovies()
            getUpComingMovies()

            scope.cancel()
        }
        else{
            scope.launch {
                // New coroutine that can call suspend functions
                offLinePopularMoviesFetched()
            }

            getPopularMovies()
            getUpComingMovies()

            scope.cancel()
        }

    }


    private fun getPopularMovies() {
        MovieRepository.getPopularMovies(
            CurrentPageNo,
            ::onPopularMoviesFetched,
            ::onError
        )
    }

    private fun getUpComingMovies() {
        MovieRepository.getPopularMovies(
            CurrentPageNo,
            ::onUpComingMoviesFetched,
            ::onError
        )
    }

    private fun onUpComingMoviesFetched(movies: MutableList<MovieInfo>) {
        adapter.appendMovies(movies)
        attachUpComingMoviesOnScrollListener()
    }


     suspend fun storePopularMovies(){
        MovieRepository.storePopularMovies(
                ::onStorePopularMovies,
                ::onError
        )
    }

    suspend fun storeUpComingMovies(){
        MovieRepository.storePopularMovies(
            ::onStoreUpComingMovies,
            ::onError
        )
    }

    private fun onStoreUpComingMovies(movies: MutableList<MovieInfo>){
        for (item in movies) {
            dbHelper.insertAllMovie(item.mTittle,item.mID,item.mDescription,item.mPoster,item.mBD,item.mRating,item.mReleaseD)
        }
        Toast.makeText(this, "Loading Completed", Toast.LENGTH_SHORT).show()
    }

    private fun onStorePopularMovies(movies: MutableList<MovieInfo>){
        for (item in movies) {
            dbHelper.insertAllMovie(item.mTittle,item.mID,item.mDescription,item.mPoster,item.mBD,item.mRating,item.mReleaseD)
        }
        Toast.makeText(this, "Loading Completed", Toast.LENGTH_SHORT).show()
    }

    private fun onPopularMoviesFetched(movies: MutableList<MovieInfo>) {
        adapter.appendMovies(movies)
        attachPopularMoviesOnScrollListener()
    }

    private fun getCount() : Int{
        val response = dbHelper.allData
        return response.count
    }

    private suspend fun offLinePopularMoviesFetched(){

       val response = dbHelper.allData
       val movies : MutableList<MovieInfo> = mutableListOf()

        //Toast.makeText(this, "You are using OFFLINE", Toast.LENGTH_SHORT).show()

        if(response.count == 0){
            //Toast.makeText(this, "No Data", Toast.LENGTH_LONG).show()
        }
        else{

            while(response.moveToNext()){
                movies.add(MovieInfo(response.getLong(response.getColumnIndex("_id")),response.getString(response.getColumnIndex("_title")),response.getString(response.getColumnIndex("_description")),
                response.getString(response.getColumnIndex("_poster")),response.getString(response.getColumnIndex("_back_drop"))
                ,response.getFloat(response.getColumnIndex("_rating")),response.getString(response.getColumnIndex("_release_date"))))
            }

            //Toast.makeText(this, "Size - " + movies.size, Toast.LENGTH_LONG).show()

            adapter.appendMovies(movies)


        }

    }

    private fun onError() {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
    }

    private fun attachUpComingMoviesOnScrollListener() {
        Upview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = LayoutManger.itemCount
                val visibleItemCount = LayoutManger.childCount
                val firstVisibleItem = LayoutManger.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    Upview.removeOnScrollListener(this)
                    CurrentPageNo++
                    getUpComingMovies()
                }
            }
        })
    }

    private fun attachPopularMoviesOnScrollListener() {
        Rview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = LayoutManger.itemCount
                val visibleItemCount = LayoutManger.childCount
                val firstVisibleItem = LayoutManger.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    Rview.removeOnScrollListener(this)
                    CurrentPageNo++
                    getPopularMovies()
                }
            }
        })
    }

    override fun onCellClickListener(data: MovieInfo) {

        val intent = Intent(this, MainActivity2::class.java).apply {
           putExtra(SELECTED_MOVIE_INFO, data)
        }
        startActivity(intent)
    }




}
