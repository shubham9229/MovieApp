package com.example.testapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.testapp.presentation.viewmodel.MovieViewModel
import com.example.testapp.databinding.FragmentMovieDetailsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment: Fragment() {
    private lateinit var binding: FragmentMovieDetailsBinding
    private val viewModel: MovieViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imdbID = arguments?.getString("id") ?: ""
        viewModel.getMovieDetails(imdbID)

        viewModel.selectedMovieDetails.observe(viewLifecycleOwner) { details ->
            binding.title.text = details.Title
            binding.genre.text = details.Genre
            binding.plot.text = details.Plot
            Glide.with(binding.poster.context).load(details.Poster).into(binding.poster)
        }
    }

}