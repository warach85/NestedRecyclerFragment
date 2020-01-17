package com.example.nestedrecyclerfragment

import com.google.firebase.database.Exclude
import java.time.LocalDate

class ItemData {
    var image:String?=null
    var date:String?=null
    // Copied on 17,12,2019 from NestedRecyclerView project
    var venue:String?=null
    //Copied below 3 on 17/01/2020
    var frequency:String?=null  // for events without date(regular events)
    var desc:String?=null
    var textitem:String?=null

    @Exclude
    var date_date: LocalDate?=null // Copied on 17,12,2019

}