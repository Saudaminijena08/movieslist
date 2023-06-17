package com.crvelox.movietest

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.crvelox.movietest.data.remote.res.SearchItem
import com.crvelox.movietest.data.remote.res.SearchResponse
import com.crvelox.movietest.domain.UseCase
import com.velox.lazeir.utils.handleFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel(){

    val loading = mutableStateOf(false)
    val currentPage = mutableStateOf(1)
    val itemList = mutableStateOf(mutableListOf<SearchItem>())

    fun getSearchUser(
        pageNo: Int,
        type: String,
        onLoading: (it: Boolean) -> Unit={},
        onFailure: (it: String, er: JSONObject) -> Unit={_,_->},
        onSuccess: (it: SearchResponse) -> Unit={},
    ) {
        loading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            useCase.searchResultUseCase.invoke(
                pageNo,
                type
            ).handleFlow(onLoading = {
                onLoading(it)
                loading.value = it
            }, onFailure = { msg, obj, code ->
                onFailure(msg, obj)
            },
                onSuccess = {
                    onSuccess(it)
                it.search?.let { it1 -> itemList.value.addAll(it1) }
            })
        }

    }
}