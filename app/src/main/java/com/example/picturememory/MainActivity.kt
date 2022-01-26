package com.example.picturememory

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog.*

class MainActivity : AppCompatActivity() {

   private lateinit var memoryGame:MemoryGame
   private lateinit var adapter:Myadapter
   private lateinit var list:List<Memory>
   private lateinit var boardSize: BoardSize

   private lateinit var b:BoardSize
    private var prev: String? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        var i=intent.getStringExtra("level")

        when(i){
             "easy"->{

                 prev="easy"
             }
            "meduim"->{

                prev="medium"
            }
            "hard"->{
                prev="hard"
            }
        }
        setUpGame()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list,menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
               R.id.restart->{
                   if(!memoryGame.isWon()){
                       AlertDialogShow("Quit your current game?",null,View.OnClickListener {
                           setUpGame()
                       })
                   }
                   else {
                       setUpGame()
                   }
               }


        }
        return super.onOptionsItemSelected(item)
    }

    private fun AlertDialogShow(title:String, view: View?, postiveClickListener: View.OnClickListener) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setView(view)
            .setNegativeButton("cancel",null)
            .setPositiveButton("OK"){_,_->
               postiveClickListener.onClick(null)
            }.show()


    }

    @SuppressLint("SetTextI18n")
    private fun setUpGame(){
        when(prev){
            "easy"->{

                b=BoardSize.Easy
            }
            "meduim"->{

                b=BoardSize.Meduim
            }
            "hard"->{

                b=BoardSize.Hard
            }

        }
        pairs.text="Pairs:0"
        moves.text="Moves:0"
        memoryGame=MemoryGame(b)
        boardSize=b
        list=memoryGame.cards

        adapter=Myadapter(this,boardSize ,memoryGame.cards,object:Myadapter.CardClickListener{
            override fun cardClicked(position: Int) {
                Flip(position)
            }

        })
        rcview.adapter=adapter
        rcview.setHasFixedSize(true)
        rcview.layoutManager=GridLayoutManager(this,boardSize.width())
    }
    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    private fun Flip(position:Int){
              if(memoryGame.isWon()){

                val dialog=Dialog(this)
                  dialog.setCancelable(false)
                  dialog.setContentView(R.layout.dialog)
                  dialog.show()

                  return
              }
        if(memoryGame.isUp(position)){
            Snackbar.make(Clayout,"INVALID MOVE",Snackbar.LENGTH_SHORT).show()
            return
        }
            if( memoryGame.flipCard(position)){
             pairs.text="Pairs:${memoryGame.pairs}/${boardSize.NumPairs()}"
                if(memoryGame.isWon()){
                    val dialog=Dialog(this)
                    dialog.setCancelable(false)
                    dialog.setContentView(R.layout.dialog)
                    dialog.show()
                }
            }
           moves.text="Moves:${memoryGame.getTotalMoves()}"
              adapter.notifyDataSetChanged()
    }



}