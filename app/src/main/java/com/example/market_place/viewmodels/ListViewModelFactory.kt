package com.example.market_place.viewmodels

import androidx.lifecycle.ViewModel
import com.example.market_place.repository.Repository
import androidx.lifecycle.ViewModelProvider.Factory


class ListViewModelFactory(private val repository: Repository) : Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListViewModel(repository) as T
    }
}