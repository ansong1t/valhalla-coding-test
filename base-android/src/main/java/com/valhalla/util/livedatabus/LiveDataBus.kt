package com.valhalla.util.livedatabus

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

object LiveDataBus {
    private val subjectMap = HashMap<Event, EventLiveData>()

    /**
     * Get the live data or create it if it's not already in memory.
     */
    fun getLiveData(subjectCode: Event): EventLiveData {
        var liveData: EventLiveData? = subjectMap[subjectCode]
        if (liveData == null) {
            liveData =
                EventLiveData(
                    subjectCode
                )
            subjectMap[subjectCode] = liveData
        }

        return liveData
    }

    /**
     * Subscribe to the specified subject and listen for updates on that subject.
     */
    fun subscribe(subject: Event, lifecycle: LifecycleOwner, action: Observer<ConsumableEvent>) {
        try {
            // avoid register same instance
            getLiveData(
                subject
            ).observe(lifecycle, action)
        } catch (throwable: IllegalArgumentException) {
            throwable.printStackTrace()
        }
    }

    inline fun subscribe(
        subject: Event,
        lifecycle: LifecycleOwner,
        crossinline onChanged: (ConsumableEvent) -> Unit
    ) {
        try {

            val wrappedObserver = Observer<ConsumableEvent> { t -> onChanged.invoke(t) }
            // avoid register same instance
            getLiveData(
                subject
            ).observe(lifecycle, wrappedObserver)
        } catch (throwable: IllegalArgumentException) {
            throwable.printStackTrace()
        }
    }

    /**
     * Removes this subject when it has no observers.
     */
    fun unregister(subject: Event) {
        subjectMap.remove(subject)
    }

    /**
     * Publish an object to the specified subject for all subscribers of that subject.
     */
    fun publish(subject: Event, message: ConsumableEvent = ConsumableEvent()) {
        getLiveData(
            subject
        ).update(message)
    }
}
