package com.valhalla.util.livedatabus

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

class EventLiveData(private val mSubject: Event) : LiveData<ConsumableEvent>() {

    fun update(obj: ConsumableEvent) {
        postValue(obj)
    }

    override fun removeObserver(observer: Observer<in ConsumableEvent>) {
        super.removeObserver(observer)
        if (!hasObservers()) {
            LiveDataBus.unregister(
                mSubject
            )
        }
    }
}
