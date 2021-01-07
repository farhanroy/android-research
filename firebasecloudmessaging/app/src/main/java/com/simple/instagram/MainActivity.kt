package com.simple.instagram

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.ktx.Firebase
import com.simple.instagram.util.Constants
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var tokens: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                val token = task.result?.token
                tokens = token.toString()
                token?.let { saveNewToken(it) }
            })

        btn_send.setOnClickListener {
            Thread(Runnable { pushNotification() }).start()
        }
    }

    private fun pushNotification() {
        val jPayload = JSONObject()
        val jNotification = JSONObject()
        val jData = JSONObject()
        try {
            jNotification.put("title", "Google I/O 2016")
            jNotification.put("body", "Firebase Cloud Messaging (App)")

            val ja = JSONArray()
            ja.put(tokens)
            jPayload.put("registration_ids", ja)

            jPayload.put("priority", "high")
            jPayload.put("notification", jNotification)
            jPayload.put("data", jData)
            val url = URL("https://fcm.googleapis.com/fcm/send")
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.setRequestProperty("Authorization", Constants.SERVER_KEY)
            conn.setRequestProperty("Content-Type", "application/json")
            conn.doOutput = true

            // Send FCM message content.
            val outputStream: OutputStream = conn.outputStream
            outputStream.write(jPayload.toString().toByteArray())

            // Read FCM response.
            val inputStream: InputStream = conn.inputStream
            val resp: String = convertStreamToString(inputStream)
            val h = Handler(Looper.getMainLooper())
            h.post { Log.d("",resp) }
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun convertStreamToString(`is`: InputStream): String {
        val s: Scanner = Scanner(`is`).useDelimiter("\\A")
        return if (s.hasNext()) s.next().replace(",", ",\n") else ""
    }

    private fun saveNewToken(token: String) {
        val ref = Firebase.firestore
            .collection("fire_notify").document("fcm")
        ref.update("token", token)
            .addOnSuccessListener { Log.d("", "Success update document") }
            .addOnFailureListener { e -> Log.w("", "Error update document", e) }
    }

}