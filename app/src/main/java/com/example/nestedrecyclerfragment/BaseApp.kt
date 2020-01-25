package com.example.nestedrecyclerfragment

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging

class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()

        //region Creating notification channel and Topic Subscription
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val mChannel =  NotificationChannel("MyNotification","MyNotification",
                NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager = getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
        FirebaseMessaging.getInstance().subscribeToTopic("general")
            .addOnCompleteListener { task ->
                var msg = "Successful"
                if (!task.isSuccessful) {
                    msg = "Failed"
                }
                //Log.d(ContentValues.TAG, msg)
                //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            }

    }

}