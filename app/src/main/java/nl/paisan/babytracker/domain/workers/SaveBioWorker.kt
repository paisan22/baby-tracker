package nl.paisan.babytracker.domain.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class SaveBioWorker(
    private val context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    private val TAG = "SaveBioWorker"

    override fun doWork(): Result {

        Log.d(TAG, "doWork() called")

        return Result.success()
    }
}