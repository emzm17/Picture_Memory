package com.example.picturememory.dao


import com.example.picturememory.models.Score
import com.example.picturememory.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*

class ScoreDao {

    val dbScore=FirebaseFirestore.getInstance()
    val scoreList=dbScore.collection("scores")
    val auth=FirebaseAuth.getInstance()

    @DelicateCoroutinesApi
    fun addscore(numbersteps: Long,level:String){
        GlobalScope.launch(Dispatchers.IO) {
            val currentUser = auth.currentUser
            val currentTime = System.currentTimeMillis()
            val post = Score(numbersteps,currentUser!!.uid , currentTime,level,currentUser.displayName)
            scoreList.document().set(post)
        }

    }

}