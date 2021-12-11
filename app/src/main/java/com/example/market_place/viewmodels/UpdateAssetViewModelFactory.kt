package com.example.market_place.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.market_place.repository.Repository

class UpdateAssetViewModelFactory(private val context: Context, private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UpdateAssetViewModel(context, repository) as T
    }
}