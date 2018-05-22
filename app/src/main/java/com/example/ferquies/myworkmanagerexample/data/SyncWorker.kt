package com.example.ferquies.myworkmanagerexample.data

import androidx.work.Data
import androidx.work.Worker

class SyncWorker : Worker() {
    override fun doWork(): WorkerResult {
        val input = inputData.getString(INPUT_PARAM, "")
        // TODO: Do things with input data

        val output = Data.Builder()
                .putString(OUTPUT, "Imagine this data is retrieved from network...")
                .build()
        outputData = output

        return WorkerResult.SUCCESS
    }

    companion object {
        const val INPUT_PARAM = "input_param"
        const val OUTPUT = "output"
    }
}