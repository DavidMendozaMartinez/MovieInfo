package com.davidmendozamartinez.movieinfo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.davidmendozamartinez.movieinfo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val sampleMovies: List<Movie> = listOf(
            Movie(1, "Mean Streets", "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/gSnb0P5xAXXeSqdXe2nPmkJOy5C.jpg"),
            Movie(2, "Taxi Driver", "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/eQ5SVeWsl8bIL3K4TPQC4McA9SG.jpg"),
            Movie(3, "Raging Bull", "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/uTrGbVJMcINTZaMW3J6PJ7kZlV6.jpg")
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupList()
    }

    private fun setupList() {
        val adapter = MovieAdapter()
        binding.movieList.adapter = adapter
        adapter.submitList(sampleMovies)
    }
}