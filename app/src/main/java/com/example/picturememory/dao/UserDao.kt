package com.example.picturememory.dao

import com.example.picturememory.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.okhttp.internal.DiskLruCache

import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserDao {
   private val db=FirebaseFirestore.getInstance()
   private  val usersList=db.collection("users")

    fun addUsers(user: User?){
        user?.let {
            GlobalScope.launch(Dispatchers.IO){
                usersList.document(user.uid).set(it)
            }
        }
    }

}