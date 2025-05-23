package com.example.newsaggregator.feature_news_main.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsaggregator.R
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

    private val viewModel: NewsViewModel by activityViewModels()

    private var _binding: FragmentNewsMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val items = resources.getStringArray(R.array.simple_items)
        val adapterCompleteText =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)
        binding.autoCompleteTextView.setAdapter(adapterCompleteText)
        binding.autoCompleteTextView.hint = items[0]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.newsRecyclerView
        val navController = findNavController()
        val adapter =
            ArticleListAdapter { url ->
                navigateToArticleDetail(url, navController)
            }
        recyclerView.let {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.adapter = adapter
            it.addItemDecoration(NewsListItemDecoration(bottomOffset = 12))
        }

        //Обработчик свайпа для обновления данных
        val swipeRefresh = binding.swipeRefresh
        swipeRefresh.setOnRefreshListener {
            if (viewModel.searchQuery.value.isBlank()){
                viewModel.getNews()
            }
            swipeRefresh.isRefreshing = false
        }

        //Наблюдение за изменениями в данных
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest {
                adapter.articleList = it.articles
                binding.progressBar.isVisible = it.isLoading
                recyclerView.scrollToPosition(0)
            }
        }

        //Обработчик нажатия на выпадающее меню
        binding.autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            val filter = when (position) {
                0 -> Filter.DESC_DATE
                1 -> Filter.ASC_DATE
                else -> Filter.DESC_DATE
            }
            viewModel.setFilter(filter)
            viewModel.observeFilter()
        }

        //Наблюдение за ошибками
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is UiEvent.ShowSnackbar -> {
                        showSnackbar(event.message)
                    }
                }
            }
        }

        //Обработка поисковых запросов
        binding.searchEditText.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s?.toString() ?: ""
                viewModel.searchQuery.value = query
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(p0: Editable?) {}

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setAction("Retry") {
                viewModel.getNews()
            }
            .show()
    }

}

private fun navigateToArticleDetail(url: String, navController: NavController) {
    val action = NewsMainFragmentDirections.navigateToDetailsFragment(url)
    navController.navigate(action)
}