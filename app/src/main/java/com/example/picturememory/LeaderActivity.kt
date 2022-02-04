package com.example.picturememory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.picturememory.dao.ScoreDao
import com.example.picturememory.models.Score

class LeaderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader)
        val scoreDao=ScoreDao()
        val l=scoreDao.scoreList

    }
}