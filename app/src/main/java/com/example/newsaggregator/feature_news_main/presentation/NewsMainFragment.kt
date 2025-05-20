package com.example.newsaggregator.feature_news_main.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsaggregator.databinding.FragmentNewsMainBinding
import com.example.newsaggregator.feature_news_main.presentation.recyclerview.ArticleListAdapter
import com.example.newsaggregator.feature_news_main.presentation.recyclerview.NewsListItemDecoration
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsMainFragment : Fragment() {

    private val viewModel: NewsViewModel by viewModels()

    private var _binding: FragmentNewsMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArticleListAdapter()
        binding.newsRecyclerView.adapter = adapter

        val swipeRefresh = binding.swipeRefresh

        swipeRefresh.setOnRefreshListener {
            viewModel.getNews()
            swipeRefresh.isRefreshing = false
        }

        val recyclerView = binding.newsRecyclerView
        recyclerView.let {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.adapter = adapter
            it.addItemDecoration(NewsListItemDecoration(bottomOffset = 100))
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest {
                adapter.articleList = it.articles
                binding.progressBar.isVisible = it.isLoading
            }
        }



        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is UiEvent.ShowSnackbar -> {
                        showSnackbar(event.message)
                    }
                }
            }
        }

    }


    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setAction("Retry") {
                viewModel.getNews()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}