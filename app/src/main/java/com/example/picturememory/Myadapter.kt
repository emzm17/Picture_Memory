package com.example.picturememory

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.picturememory.Constant.Companion.Margin

import kotlin.math.min

class Myadapter(
    private val context: Context,
    private val num: BoardSize,
    val image: List<Memory>,
    private val cardClickListener: CardClickListener
):RecyclerView.Adapter<Myadapter.MyViewHolder> (){

    interface CardClickListener{
        fun cardClicked(position:Int)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val w=parent.width/num.width()-(2* Margin)
       val h=parent.height/num.height()-(2* Margin)
       val m=min(w,h)
       var v= LayoutInflater.from(context).inflate(R.layout.gridlist,parent,false)
       val layoutview=v.findViewById<CardView>(R.id.cardView).layoutParams as ViewGroup.MarginLayoutParams
       layoutview.width=m
       layoutview.height=m
       layoutview.setMargins(Margin, Margin, Margin, Margin)

     return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.bind(position)
    }

    override fun getItemCount(): Int {
       return num.cards
    }

inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val btn = itemView.findViewById<ImageButton>(R.id.imageButton)
    val mediaPlayer= MediaPlayer.create(context,R.raw.click)
    fun bind(i: Int) {
        btn.setImageResource( if(image[i].up) image[i].id else R.color.teal_100)
        btn.alpha = if(image[i].matched) .4f else 1.0f
        val colorState= if(image[i].matched ) ContextCompat.getColorStateList(context,R.color.teal_700) else null
        ViewCompat.setBackgroundTintList(btn,colorState)

        btn.setOnClickListener {
            Log.d("TAG", "Clicked button $i")
              cardClickListener.cardClicked(i)
                mediaPlayer.start()

        }
    }
}
}