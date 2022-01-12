package com.valhalla.domain

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.RemoteMediator
import com.valhalla.base.InvokeError
import com.valhalla.base.InvokeStarted
import com.valhalla.base.InvokeStatus
import com.valhalla.base.InvokeSuccess
import com.valhalla.data.entities.ErrorResult
import com.valhalla.data.entities.Result
import com.valhalla.data.entities.Success
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeout
import java.util.concurrent.TimeUnit

abstract class Interactor<in P> {
    operator fun invoke(params: P, timeoutMs: Long = defaultTimeoutMs): Flow<InvokeStatus> {
        return flow {
            try {
                withTimeout(timeoutMs) {
                    emit(InvokeStarted)
                    try {
                        doWork(params)
                        emit(InvokeSuccess)
                    } catch (t: Throwable) {
                        emit(InvokeError(t))
                    }
                }
            } catch (t: TimeoutCancellationException) {
                emit(InvokeError(t))
            }
        }
    }

    suspend fun executeSync(params: P) = doWork(params)

    protected abstract suspend fun doWork(params: P)

    companion object {
        private val defaultTimeoutMs = TimeUnit.MINUTES.toMillis(5)
    }
}

abstract class ResultInteractor<in P, R> {
    operator fun invoke(params: P): Flow<Result<R>> {
        return flow {
            try {
                emit(Success(doWork(params)))
            } catch (e: Exception) {
                emit(ErrorResult<R>(e))
            }
        }
    }

    protected abstract suspend fun doWork(params: P): R
}

@ExperimentalPagingApi
abstract class PagingInteractor<P : PagingInteractor.Parameters<KEY, T>, KEY : Any, T : Any> :
    SubjectInteractor<P, PagingData<T>>() {
    interface Parameters<KEY : Any, T : Any> {
        val pagingConfig: PagingConfig
        val coroutineScope: CoroutineScope
        val pagingSource: PagingSource<KEY, T>
        val remoteMediator: RemoteMediator<KEY, T>?
    }
}

abstract class SuspendingWorkInteractor<P : Any, T> : SubjectInteractor<P, T>() {
    override fun createObservable(params: P): Flow<T> = flow {
        emit(doWork(params))
    }

    abstract suspend fun doWork(params: P): T
}

abstract class SubjectInteractor<P : Any, T> {
    // Ideally this would be buffer = 0, since we use flatMapLatest below, BUT invoke is not
    // suspending. This means that we can't suspend while flatMapLatest cancels any
    // existing flows. The buffer of 1 means that we can use tryEmit() and buffer the value
    // instead, resulting in mostly the same result.
    private val paramState = MutableSharedFlow<P>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    operator fun invoke(params: P) {
        val emitted = paramState.tryEmit(params)
        Log.e("SubjectInteractor","emitted = $emitted")
    }

    protected abstract fun createObservable(params: P): Flow<T>

    fun observe(): Flow<T>  {
        return paramState.flatMapLatest {
            createObservable(it)
        }
    }
}
