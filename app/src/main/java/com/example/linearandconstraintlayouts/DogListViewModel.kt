package com.example.linearandconstraintlayouts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linearandconstraintlayouts.adapter.DogListRecyclerViewAdapter
import com.example.linearandconstraintlayouts.data.entities.Breed
import com.example.linearandconstraintlayouts.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _listItems by lazy {
       MutableLiveData<List<DogListRecyclerViewAdapter.DogItem>>()
    }

    val listItems: LiveData<List<DogListRecyclerViewAdapter.DogItem>>
        get() = _listItems

    private val _selectedDogDetail by lazy {
        MutableLiveData<Breed?>()
    }

    val selectedDogDetail: LiveData<Breed?>
        get() = _selectedDogDetail

    fun fetchDogs() {
        viewModelScope.launch {
            _listItems.postValue(repository.getAllDogs().map {
                val breed = repository.getBreedById(it.breedId!!)!!
                val breedName = breed.name
                val breedImageUrl = breed.mediaId?. let { mediaId -> repository.getMediaByExternalId(mediaId)?.url }
                DogListRecyclerViewAdapter.DogItem(
                    name = it.name,
                    id = it.id!!, // Warranted to have an ID as it is coming form the DB
                    breedName = breedName,
                    imageUrl = breedImageUrl
                )
            })
        }
    }

    fun dismissDogById(id: Long) {
        viewModelScope.launch {
            repository.dismissDog(id)
            fetchDogs()
        }
    }

    fun getDogDetailsById(id: Long) {
        viewModelScope.launch {
            val dog = repository.getDogById(id)!!
            val breed = repository.getBreedById(dog.breedId!!)
            _selectedDogDetail.postValue(breed)
        }
    }

    fun closeDetailDialog() {
        _selectedDogDetail.value = null
    }
}