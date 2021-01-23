package com.davidmendozamartinez.movieinfo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.davidmendozamartinez.movieinfo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val sampleMovieUIS: List<MovieUI> = listOf(
        MovieUI(
            1,
            "Mean Streets",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/gSnb0P5xAXXeSqdXe2nPmkJOy5C.jpg"
        ),
        MovieUI(
            2,
            "Taxi Driver",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/eQ5SVeWsl8bIL3K4TPQC4McA9SG.jpg"
        ),
        MovieUI(
            3,
            "Raging Bull",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/uTrGbVJMcINTZaMW3J6PJ7kZlV6.jpg"
        )
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
        adapter.submitList(sampleMovieUIS)
    }
}