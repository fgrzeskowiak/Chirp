package com.filippo.core.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import org.koin.compose.viewmodel.koinViewModel
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
@OptIn(ExperimentalUuidApi::class)
fun DialogSheetScopedViewModel(
    isVisible: Boolean,
    scopeId: String = rememberSaveable { Uuid.random().toString() },
    content: @Composable () -> Unit,
) {
    val parentOwner = checkNotNull(LocalViewModelStoreOwner.current)
    val registry = koinViewModel<ScopedStoreRegistryViewModel>(
        viewModelStoreOwner = parentOwner,
    )

    var owner by remember { mutableStateOf<ViewModelStoreOwner?>(null) }

    LaunchedEffect(isVisible) {
        when {
            isVisible && owner == null -> {
                owner = object : ViewModelStoreOwner {
                    override val viewModelStore = registry.getOrCreate(scopeId)
                }
            }
            !isVisible && owner != null -> {
                registry.clear(scopeId)
                owner = null
            }
        }
    }

    owner?.let { it
        CompositionLocalProvider(LocalViewModelStoreOwner provides it) {
            content()
        }
    }
}