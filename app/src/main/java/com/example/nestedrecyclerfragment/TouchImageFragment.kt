package com.example.nestedrecyclerfragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * A simple [Fragment] subclass.
 */
class TouchImageFragment : Fragment() {

    lateinit var imageUrl:String
    lateinit var descText:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_touch_image, container, false)
        var imgView = v.findViewById<TouchImageView>(R.id.touchImageView)
        imgView.setMaxZoom(3f)

        if(arguments!=null) {if (arguments?.keySet()!!.contains("imageUrl")) {imageUrl = arguments?.get("imageUrl").toString(); descText = arguments?.get("notifText").toString() } // foreground notification(notif image)
            else if (arguments?.keySet()!!.contains("photoUrl")) {imageUrl = arguments?.get("photoUrl").toString(); descText = arguments?.get("notifText").toString()} //background notification(custome image)
            else if (arguments?.keySet()!!.contains("dbImageUrl")) {imageUrl = arguments?.get("dbImageUrl").toString(); descText = arguments?.get("desc").toString() } //From RecyclerVw Item click


            GlideApp
                .with(this)
                .load(imageUrl)
                .into(imgView)
        }

        return v
    }


}
