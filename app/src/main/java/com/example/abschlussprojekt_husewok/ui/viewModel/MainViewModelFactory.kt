package com.example.abschlussprojekt_husewok.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.abschlussprojekt_husewok.data.repository.Repository

/**
 * Factory class for creating instances of MainViewModel.
 *
 * @param repository The repository.
 */
class MainViewModelFactory(
    /*private val application: Application*/
    private val repository: Repository
) : ViewModelProvider.Factory {
    /**
     * Creates a new instance of MainViewModel.
     *
     * @param modelClass The class of the ViewModel to create.
     * @return The created instance of MainViewModel.
     * @throws IllegalArgumentException if the modelClass is not MainViewModel.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}