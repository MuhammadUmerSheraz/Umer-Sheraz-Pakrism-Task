package umer.task.pakrism.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import umer.task.pakrism.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.core.KoinComponent
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), KoinComponent, CoroutineScope {

    var isLoading = MutableLiveData<Boolean>()
    val isError = SingleLiveEvent<String>()

    private val job = Job()
    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        // Clear our job when the linked activity is destroyed to avoid memory leaks
        job.cancel()
    }

}
