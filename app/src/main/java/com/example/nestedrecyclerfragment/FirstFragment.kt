package com.example.nestedrecyclerfragment


import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nestedrecyclerfragment.Adapters.MyGroupAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment : Fragment(),IFirebaseLoadListener {

    override fun onFirebaseLoadSuccess(itemGroupList: List<ItemGroup>) {
        val adapter = MyGroupAdapter(activity!!,itemGroupList) // added exclaimation to remove error
        my_recycler_view.adapter = adapter
    }

    override fun onFirebaseLoadFailed(message: String) {
        Toast.makeText(activity,message, Toast.LENGTH_SHORT).show()
    }

    lateinit var my_recycler_view: RecyclerView
    lateinit var iFirebaseLoadListener: IFirebaseLoadListener
    lateinit var myData: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

         val v:View = inflater.inflate(R.layout.fragment_first, container, false)
         my_recycler_view=v.findViewById(R.id.my_recycler_view)



        myData = FirebaseDatabase.getInstance().getReference("MyData")
        iFirebaseLoadListener = this

        //view
        my_recycler_view.setHasFixedSize(true)
        my_recycler_view.layoutManager = LinearLayoutManager(activity)

        getFireBaseData()

        return v
    }


    private fun getFireBaseData(){
        myData.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val itemGroups = ArrayList<ItemGroup>()
                for(myDataSnapShot in p0.children)
                {
                    val itemGroup = ItemGroup()
                    itemGroup.headerTitle = myDataSnapShot.child("headerTitle")
                        .getValue(true).toString()
                    val t = object: GenericTypeIndicator<ArrayList<ItemData>>(){}
                    itemGroup.listItem = myDataSnapShot.child("listItem").getValue(t)
                    itemGroups.add(itemGroup)
                }
                iFirebaseLoadListener.onFirebaseLoadSuccess(itemGroups)
            }

            override fun onCancelled(p0: DatabaseError) {
                iFirebaseLoadListener.onFirebaseLoadFailed(p0.message)
            }

        })
    }

}
