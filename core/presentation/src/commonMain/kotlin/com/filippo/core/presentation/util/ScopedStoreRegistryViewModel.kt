package com.filippo.core.presentation.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ScopedStoreRegistryViewModel : ViewModel() {
    private val stores = mutableMapOf<String, ViewModelStore>()

    fun getOrCreate(id: String) = stores.getOrPut(id) { ViewModelStore() }

    fun clear(id: String) {
        stores.remove(id)?.clear()
    }

    override fun onCleared() {
        stores.forEach { it.value.clear() }
        stores.clear()
    }
}