package com.example.nestedrecyclerfragment

interface IFirebaseLoadListener {
    fun onFirebaseLoadSuccess(itemGroupList:List<ItemGroup>)
    fun onFirebaseLoadFailed(message:String)
}