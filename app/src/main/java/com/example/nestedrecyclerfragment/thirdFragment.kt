package com.example.nestedrecyclerfragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_third.*

/**
 * A simple [Fragment] subclass.
 */
class thirdFragment : Fragment() {

    lateinit var imageUrl:String
    lateinit var descText:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v : View = inflater.inflate(R.layout.fragment_third, container, false)
        val thirdFragImageView = v.findViewById<ImageView>(R.id.thirdFragImageView)
        val thirdFragTextView = v.findViewById<TextView>(R.id.thirdFragTextView)

        if(arguments!=null)
        {
            if     (arguments?.keySet()!!.contains("imageUrl"))  {imageUrl = arguments?.get("imageUrl").toString();   descText = arguments?.get("notifText").toString()} // foreground notification(notif image)
            else if(arguments?.keySet()!!.contains("photoUrl"))  {imageUrl = arguments?.get("photoUrl").toString();   descText = arguments?.get("notifText").toString()} //background notification(custome image)
            else if(arguments?.keySet()!!.contains("dbImageUrl")){imageUrl = arguments?.get("dbImageUrl").toString(); descText = arguments?.get("desc").toString()} //From RecyclerVw Item click


            GlideApp
                .with(this)
                .load(imageUrl)
                .into(thirdFragImageView)

            thirdFragTextView.setText(descText)
        }

        return v
    }


}
