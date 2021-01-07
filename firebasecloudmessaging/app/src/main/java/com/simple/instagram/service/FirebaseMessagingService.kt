package com.simple.instagram.service

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.simple.instagram.R


class FirebaseMessagingService : FirebaseMessagingService() {

    private val db = Firebase.firestore

    override fun onMessageReceived(rm: RemoteMessage) {
        super.onMessageReceived(rm)
        Log.d("SEND", "Success")
        createNotification(rm.notification)
    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        Log.d("TOKEN", newToken)
        saveNewToken(newToken)
    }

    private fun createNotification(remoteMessage: RemoteMessage.Notification?) {
        val builder = Notification.Builder(this)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(remoteMessage?.title)
            .build()

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0, builder)

    }

    private fun saveNewToken(token: String) {
        val ref = db.collection("fire_notify").document("fcm")
        ref.update("token", token)
            .addOnSuccessListener { Log.d("", "Success update document") }
            .addOnFailureListener { e -> Log.w("", "Error update document", e) }
    }
}