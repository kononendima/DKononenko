package com.example.tinkoffapp.ui.hot_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinkoffapp.data.Model
import com.example.tinkoffapp.data.PostResult
import com.example.tinkoffapp.ui.IRepository
import com.example.tinkoffapp.ui.view_states.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentViewModel(private val repository: IRepository<List<Model>>) : ViewModel() {
    private val _stateLiveData = MutableLiveData<ViewState>()
    val stateLiveData: LiveData<ViewState> = _stateLiveData

    private var page = 0

    fun resetPage() {
        page = 0
    }

    fun start() {
        _stateLiveData.postValue(ViewState())
    }

    fun nextItem() {
        _stateLiveData.value = ViewState(isNext = true)
    }

    fun prevItem() {
        _stateLiveData.value = ViewState(isPrev = true)
    }

    fun reloadItem() {
        _stateLiveData.value = ViewState(isReload = true)
    }

    fun getPosts(type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _stateLiveData.postValue(ViewState(isLoading = true))
            when (val result = repository.getData(type, page++)) {
                is PostResult.Success -> {
                    if (result.items.isNotEmpty()) {
                        _stateLiveData.postValue(
                            ViewState(
                                items = result.items,
                                isSuccess = true
                            )
                        )
                    } else {
                        page--
                        _stateLiveData.postValue(ViewState(isEmpty = true))
                    }
                }
                is PostResult.Error -> {
                    page--
                    _stateLiveData.postValue(ViewState(isError = true))
                }
            }
        }
    }
}