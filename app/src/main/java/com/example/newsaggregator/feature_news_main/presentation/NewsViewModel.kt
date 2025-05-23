package com.example.newsaggregator.feature_news_main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsaggregator.core.util.RequestResult
import com.example.newsaggregator.feature_news_main.data.repository.models.Article
import com.example.newsaggregator.feature_news_main.domain.GetNewsUseCase
import com.example.newsaggregator.feature_news_main.presentation.model.NewsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
) : ViewModel() {

    init {
        getNews()
    }

    private val _state: MutableStateFlow<NewsState> = MutableStateFlow(NewsState())
    val state: StateFlow<NewsState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    private val _filterState = MutableStateFlow(Filter.DESC_DATE)

    fun setFilter(filter: Filter) {
        _filterState.value = filter
    }

    fun observeFilter() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            delay(1000L)
            val currentArticles = _state.value.articles
            val sortedArticles = sortArticles(currentArticles, _filterState.value)
            _state.value = _state.value.copy(articles = sortedArticles, isLoading = false)
        }
    }

    fun getNews() {
        viewModelScope.launch {
            getNewsUseCase.execute("international")
                .onEach { result ->
                    when (result) {
                        is RequestResult.Success -> {
                            _state.value = _state.value.copy(
                                articles = sortArticles(
                                    result.data ?: emptyList(),
                                    _filterState.value
                                ),
                                isLoading = false
                            )
                        }

                        is RequestResult.Error -> {
                            _state.value = _state.value.copy(
                                articles = sortArticles(
                                    result.data ?: emptyList(),
                                    _filterState.value
                                ),
                                isLoading = false
                            )
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    result.message ?: "Unknown error"
                                )
                            )
                        }

                        is RequestResult.Loading -> {
                            _state.value = _state.value.copy(
                                articles = sortArticles(
                                    result.data ?: emptyList(),
                                    _filterState.value
                                ),
                                isLoading = true
                            )
                            delay(1000L)
                        }
                    }
                }.launchIn(this)
        }
    }

    private fun sortArticles(articles: List<Article>, filter: Filter): List<Article> {
        return when (filter) {
            Filter.DESC_DATE -> articles.sortedByDescending { it.publishedDate }
            Filter.ASC_DATE -> articles.sortedBy { it.publishedDate }
        }
    }
}

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
}

enum class Filter {
    DESC_DATE,
    ASC_DATE,
}