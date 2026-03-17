package com.vaultly.data.sync

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SyncManager @Inject constructor(private val context: Context) {
    fun schedule(userId: String) {
        val inputData = workDataOf(SyncWorker.USER_ID_KEY to userId)
        val request = PeriodicWorkRequestBuilder<SyncWorker>(30, TimeUnit.MINUTES)
            .setConstraints(Constraints(requiredNetworkType = NetworkType.CONNECTED))
            .setInputData(inputData)
            .build()
        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork("vaultly-sync-$userId", ExistingPeriodicWorkPolicy.UPDATE, request)
    }
}
