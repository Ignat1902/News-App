package com.example.newsaggregator.feature_news_main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsaggregator.core.util.RequestResult
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
    val getNewsUseCase: GetNewsUseCase,
) : ViewModel() {

    init {
        getNews()
    }

    private val _state: MutableStateFlow<NewsState> = MutableStateFlow(NewsState())
    val state: StateFlow<NewsState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    fun getNews() {
        viewModelScope.launch {
            getNewsUseCase.execute("international")
                .onEach { result ->
                    when (result) {
                        is RequestResult.Success -> {
                            _state.value = _state.value.copy(
                                articles = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }

                        is RequestResult.Error -> {
                            _state.value = _state.value.copy(
                                articles = result.data ?: emptyList(),
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
                                articles = result.data ?: emptyList(),
                                isLoading = true
                            )
                            delay(1000L)
                        }
                    }
                }.launchIn(this)
        }
    }


    /* val state: StateFlow<UiState> = getNewsUseCase.execute("international")
         .map { it.toUiState() }
         .stateIn(viewModelScope, SharingStarted.Lazily, UiState.None)*/


}

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
}