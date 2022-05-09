package com.example.linearandconstraintlayouts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linearandconstraintlayouts.adapter.BreedListRecyclerViewAdapter
import com.example.linearandconstraintlayouts.data.factories.BreedDaoFactory
import com.example.linearandconstraintlayouts.data.factories.DogDaoFactory
import com.example.linearandconstraintlayouts.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class BreedListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _listItems by lazy {
        MutableLiveData<List<BreedListRecyclerViewAdapter.BreedItem>>()
    }

    val listItems: LiveData<List<BreedListRecyclerViewAdapter.BreedItem>>
        get() = _listItems

    fun fetchBreeds() {
        viewModelScope.launch {
            repository.refreshBreeds()
           _listItems.postValue(repository.getAllBreeds().map { async(Dispatchers.IO) {
                BreedListRecyclerViewAdapter.BreedItem(
                    name = it.name,
                    imageUrl = it.imageUrl,
                    id = it.id!! // warranted to have an ID as it is coming from DB
                )
            } }.awaitAll() )
        }
    }

    // RunBlocking is used as I don't want to present users a view with empty objects, not even for
    // a fraction of a second.
    fun fetchBreedById(id: Long) = runBlocking {
        return@runBlocking repository.getBreedById(id)
    }

}