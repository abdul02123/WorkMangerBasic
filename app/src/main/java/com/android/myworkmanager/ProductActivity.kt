package com.android.myworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager

class ProductActivity : AppCompatActivity() {

    private lateinit var workerRequest: OneTimeWorkRequest


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initWorker()
        workManagerObserver()
    }


    private fun initWorker() {
        val constraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        workerRequest =
            OneTimeWorkRequest.Builder(ProductWorker::class.java)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(applicationContext)
            .enqueueUniqueWork(
                workerRequest.id.toString(),
                ExistingWorkPolicy.KEEP,
                workerRequest
            )
        workerRequest.id


    }

    private fun workManagerObserver() {
        val workManager = WorkManager.getInstance(this)
        val workInfoLiveData = workManager.getWorkInfoByIdLiveData(workerRequest.id)

        workInfoLiveData.observe(this) { workInfo ->
            if (workInfo != null) {
                if (workInfo.state == WorkInfo.State.SUCCEEDED) {

                } else if (workInfo.state == WorkInfo.State.FAILED) {

                }
            }
        }
    }

}