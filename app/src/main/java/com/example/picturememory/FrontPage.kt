package com.example.picturememory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import com.example.picturememory.Constant.Companion.BOARDSIZE
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.activity_front_page.*

class FrontPage : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_front_page)
        button.setOnClickListener {
             val intent= Intent(this, MainActivity::class.java,)
             intent.putExtra("level","easy")
             startActivity(intent)
        }
        button2.setOnClickListener {
            val intent= Intent(this, MainActivity::class.java,)
            intent.putExtra("level","meduim")
            startActivity(intent)
        }
        button3.setOnClickListener {
            val intent= Intent(this, MainActivity::class.java,)
            intent.putExtra("level","hard")
            startActivity(intent)
        }


    }


}