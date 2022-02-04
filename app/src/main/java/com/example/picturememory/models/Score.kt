package com.example.picturememory.models

import com.google.firebase.auth.FirebaseUser

data class Score(
    val score: Long,
    val createdBy:String,
    val date:Long=0L,
    val level:String,
    val name:String?
        )