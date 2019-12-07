package com.example.nestedrecyclerfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val gotIntent = intent
        val data = gotIntent.extras

        val resultText: TextView = findViewById(R.id.resultText)
        val resultImage: ImageView = findViewById(R.id.resultImage)

        //region Intent received on clicking image on MainActivity
        resultText.text=data?.get("desc").toString()
        GlideApp
            .with(applicationContext)
            .load(data?.get("imageUrl").toString())
            .into(resultImage)
        //endregion

        //region Intent from background notification
        if(gotIntent.getBundleExtra("NotificationBundle")!=null) {
            //Display photoUrl if its not null
            if(gotIntent.getBundleExtra("NotificationBundle")!!.containsKey("photoUrl")) {
                val imageUrl = gotIntent.getBundleExtra("NotificationBundle")?.get("photoUrl").toString()

                GlideApp
                    .with(applicationContext)
                    .load(imageUrl)
                    .into(resultImage)
            }
            //Display NotifText if its not null
            if(gotIntent.getBundleExtra("NotificationBundle")!!.containsKey("notifText")){
                resultText.text = gotIntent.getBundleExtra("NotificationBundle")?.get("notifText").toString()
            }
        }
        //endregion

        //region Intent from messagingService i.e. Foreground Notification
        if(gotIntent.getStringExtra("imageUrl")!=null)
        {
            val imageUrl:String? = gotIntent.getStringExtra("imageUrl")
            GlideApp
                .with(applicationContext)
                .load(imageUrl)
                .into(resultImage)
        }
        if(gotIntent.getStringExtra("notifText")!=null)
        {
            resultText.text = gotIntent.getStringExtra("notifText")
        }
        //endregion

    }
}
