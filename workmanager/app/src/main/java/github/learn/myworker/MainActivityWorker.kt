package github.learn.myworker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.coroutineScope
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.util.*
import kotlin.math.absoluteValue

class MainActivityWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    companion object {
        private const val CHANNEL_ID = "MainActivity"
    }

    private val notification = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle("Hello World")
        .setContentText("This is your test notification")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .build()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        createNotificationChannel()

        with(NotificationManagerCompat.from(applicationContext)) {
            notify(1, notification)
        }

        return Result.success()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Test Notification",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "This is your MainActivity's test channel"
        }
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}