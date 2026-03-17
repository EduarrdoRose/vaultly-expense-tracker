package com.vaultly.data.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.vaultly.domain.repository.TransactionRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val transactionRepository: TransactionRepository,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val userId = inputData.getString(USER_ID_KEY)
        if (userId.isNullOrBlank()) {
            Timber.w("SyncWorker missing required inputData key: %s", USER_ID_KEY)
            return Result.failure()
        }

        return runCatching { transactionRepository.syncFromRemote(userId) }
            .fold(
                onSuccess = { result -> if (result.isSuccess) Result.success() else Result.retry() },
                onFailure = {
                    Timber.e(it, "SyncWorker failed for userId=%s", userId)
                    Result.retry()
                }
            )
    }

    companion object {
        const val USER_ID_KEY = "userId"
    }
}
