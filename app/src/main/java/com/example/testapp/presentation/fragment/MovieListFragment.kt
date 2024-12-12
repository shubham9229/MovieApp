package com.example.testapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.presentation.viewmodel.MovieViewModel
import com.example.testapp.util.PaginationScrollListener
import com.example.testapp.R
import com.example.testapp.databinding.FragmentMovieListBinding
import com.example.testapp.presentation.adapter.MovieAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieListFragment : Fragment(), MovieAdapter.MovieAdapterInterface {
    private lateinit var binding: FragmentMovieListBinding
    private val viewModel: MovieViewModel by viewModel()
    private var adapter: MovieAdapter? = null
    private var enteredText = ""
    private var isViewRestored = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isViewRestored = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpInitialView()
        setUpClickListener()
        setUpObserver()
        if (isViewRestored) {
            adapter?.addMoreItems(viewModel.movieList)
        }
    }

    private fun setUpObserver() {
        viewModel.movieListLiveData.observe(viewLifecycleOwner) { consumer ->
            consumer.getContentIfNotConsumed()?.let { pair ->
                adapter?.addMoreItems(pair.first, pair.second)
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun setUpClickListener() {
        binding.goTextBox.setOnClickListener {
            val s = binding.searchBoxEt.text
            if (!s.isNullOrEmpty()) {
                enteredText = s.toString()
                viewModel.movieList.clear()
                viewModel.getMoviesList(enteredText, true)
                binding.progressBar.visibility = View.VISIBLE
            }
        }
    }

    private fun setUpInitialView() {
        adapter = MovieAdapter(this)
        binding.recyclerView.adapter = adapter
//        binding.searchBoxEt.addTextChangedListener(object : TextWatcher{
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//
//            }
//
//        })
        binding.recyclerView.addOnScrollListener(object:
            PaginationScrollListener(binding.recyclerView.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                viewModel.getMoviesList(enteredText, false)
            }

            override fun isLastPage(): Boolean {
                return viewModel.isLastPage
            }

            override fun isLoading(): Boolean {
                return viewModel.isLoading
            }

        })
    }

    override fun goToDetailsPage(imdb: String, position: Int) {
        findNavController().navigate(
            R.id.movieDetailsFragment, bundleOf(
            "id" to imdb
        ))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isViewRestored = true
    }


}