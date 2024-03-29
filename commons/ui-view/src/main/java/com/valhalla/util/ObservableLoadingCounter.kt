/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.valhalla.util

import com.valhalla.base.InvokeError
import com.valhalla.base.InvokeStarted
import com.valhalla.base.InvokeStatus
import com.valhalla.base.InvokeSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.util.concurrent.atomic.AtomicInteger

/**
 * Helper class for handling loading of a view
 * */
class ObservableLoadingCounter {
    private val count = AtomicInteger()
    private val loadingState = MutableStateFlow(count.get())

    val observable: Flow<Boolean>
        get() = loadingState.map { it > 0 }.distinctUntilChanged()

    fun addLoader() {
        loadingState.value = count.incrementAndGet()
    }

    fun removeLoader() {
        loadingState.value = count.decrementAndGet()
    }
}

suspend fun Flow<InvokeStatus>.collectInto(counter: ObservableLoadingCounter) = collect {
    when (it) {
        InvokeStarted -> counter.addLoader()
        InvokeSuccess, is InvokeError -> counter.removeLoader()
    }
}

suspend fun Flow<InvokeStatus>.collectInto(
    counter: ObservableLoadingCounter,
    block: suspend (InvokeStatus) -> Unit
) = collect {
    when (it) {
        InvokeStarted -> counter.addLoader()
        InvokeSuccess, is InvokeError -> counter.removeLoader()
    }
    block(it)
}
