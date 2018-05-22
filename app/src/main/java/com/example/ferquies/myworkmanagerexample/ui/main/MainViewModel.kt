package com.example.ferquies.myworkmanagerexample.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import androidx.work.*
import com.example.ferquies.myworkmanagerexample.data.SyncWorker

class MainViewModel : ViewModel() {

    val text: MutableLiveData<String> = MutableLiveData()

    init {
        text.value = "Press button to sync"
    }

    fun sync() {
        val inputData = Data.Builder()
                .putString(SyncWorker.INPUT_PARAM, "Input data!")
                .build()
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        val syncWork = OneTimeWorkRequest.Builder(SyncWorker::class.java)
                .setInputData(inputData)
                .setConstraints(constraints)
                .addTag("sync")
                .build()

        WorkManager.getInstance().enqueue(syncWork)

        WorkManager.getInstance().getStatusById(syncWork.id)
                .observeForever {
                    it?.let {
                        text.value = when (it.state) {
                            State.SUCCEEDED -> it.outputData.getString(SyncWorker.OUTPUT, "")
                            State.ENQUEUED -> "Waiting for internet connection..."
                            State.RUNNING -> "Sync in progress..."
                            else -> "Error"
                        }
                    }
                }
    }

}
