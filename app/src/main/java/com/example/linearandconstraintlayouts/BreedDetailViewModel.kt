package com.example.linearandconstraintlayouts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linearandconstraintlayouts.data.entities.Breed
import com.example.linearandconstraintlayouts.data.factories.DogFactory
import com.example.linearandconstraintlayouts.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedDetailViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    fun requestDog(breed: Breed) {
        viewModelScope.launch {
            val newDog = DogFactory().fromBreed(breed)
            repository.requestDog(newDog)
        }
    }
}