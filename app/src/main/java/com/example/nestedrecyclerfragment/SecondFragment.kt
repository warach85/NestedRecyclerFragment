package com.example.nestedrecyclerfragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.firebase.database.*

/**
 * A simple [Fragment] subclass.
 */
class SecondFragment : Fragment() {
    lateinit var mRef:DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v:View = inflater.inflate(R.layout.fragment_second, container, false)
        mRef = FirebaseDatabase.getInstance().reference.child("Calender")
        val imgCal = v.findViewById<ImageView>(R.id.imgCalender)

        mRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                //Nothing here
            }
            override fun onDataChange(p0: DataSnapshot) {
                GlideApp
                    .with(this@SecondFragment)
                    .load(p0.child("Url").value.toString())
                    .into(imgCal)

            }
        })
        return v
    }


}
