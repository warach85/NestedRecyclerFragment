package com.example.nestedrecyclerfragment

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Priority
import com.bumptech.glide.request.target.NotificationTarget
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)
        showNotification(p0?.notification?.title,  // p0.notification means notification data
            p0?.notification?.body,                 // ,i.e, the upper part of frbnotifconsole
            p0?.notification?.imageUrl.toString(),
            p0!!.data["notifText"].toString())  //p0.data means the custom data
    }

    public fun showNotification(title:String?, message:String?,imageUrl:String,notifText:String) {
        // region Opening Activity from Notification

        // Create an Intent for the activity you want to start with making new task
        val notifyIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra("imageUrl", imageUrl)
            putExtra("title",title)
            putExtra("message",message)
            putExtra("notifText",notifText)
        }
        // TODO : Check if last flag is ok needs FLAG_UPDATE_CURRENT
        val notifyPendingIntent = PendingIntent.getActivity(this, 0, notifyIntent, 0)

        // endregion

        //region Initialising remote views and child views
        val remoteViews: RemoteViews = RemoteViews(applicationContext.packageName,R.layout.notification_layout)
        val remoteViewsBig: RemoteViews = RemoteViews(applicationContext.packageName,R.layout.notification_big_layout)
        remoteViews.setTextViewText(R.id.textView1,title)
        remoteViews.setTextViewText(R.id.textView2,message)
        //endregion

        /*Commented on 12/10/19 fortesting automatic firebase image sending*/
        // region Notification Builder and Manager
        var builder = NotificationCompat.Builder(this, "MyNotification")
            .setSmallIcon(R.drawable.notification_icon)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(remoteViews)
            .setCustomBigContentView(remoteViewsBig)
            .setContentIntent(notifyPendingIntent)
            .setAutoCancel(true)


        val notification=builder.build()

        val manager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(999,notification)
        // endregion


        //region   NotificationTarget and Glide area
        val notificationTarget= NotificationTarget(
            this,
            R.id.notifImage,
            remoteViews,
            notification,
            999
        )
        val notificationTargetBig= NotificationTarget(
            this,
            R.id.notifBigImage,
            remoteViewsBig,
            notification,
            999
        )


        GlideApp
            .with(applicationContext)
            .asBitmap()
            .load(imageUrl)
            .priority(Priority.HIGH)
            .into(notificationTarget)

        GlideApp
            .with(applicationContext)
            .asBitmap()
            .load(imageUrl)
            .into(notificationTargetBig)
        //endregion
    }


}