package com.android.myworkmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ProductWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val productRepository: ProductRepository
) : CoroutineWorker(context, workerParameters) {


    override suspend fun doWork(): Result {
        val result = productRepository.getProductListRecord()
        try {

            if (result != null){
                val success = Data.Builder()
                    .putString("data_key", result.toString())
                    .build()
                return Result.success(success)
            }else{
                return Result.failure()
            }

        }catch (e: Exception){
            e.toString()
        }

        return Result.success()


    }
}