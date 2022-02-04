package com.example.picturememory

import android.annotation.SuppressLint
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager

import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.DelicateCoroutinesApi

class MainActivity : AppCompatActivity() {

   private lateinit var memoryGame:MemoryGame
   private lateinit var adapter:Myadapter
   private lateinit var list:List<Memory>
   private lateinit var boardSize: BoardSize
   private lateinit var b:BoardSize
    private var prev: String? =null
    companion object{
        const val TAG="MAINACTIVITY"
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
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
            "medium"->{

                b=BoardSize.Meduim
            }
            "hard"->{

                b=BoardSize.Hard
            }

        }
        pairs.text="Pairs:0"
        timetaken.text="Time:00:00"
        memoryGame=MemoryGame(b)
        boardSize=b
        list=memoryGame.cards
        timetaken.base=SystemClock.elapsedRealtime()
        timetaken.start()
        adapter=Myadapter(this,boardSize ,memoryGame.cards,object:Myadapter.CardClickListener{
            override fun cardClicked(position: Int) {
                Flip(position)
            }

        })
        rcview.adapter=adapter
        rcview.setHasFixedSize(true)
        rcview.layoutManager=GridLayoutManager(this,boardSize.width())

    }


    @DelicateCoroutinesApi
    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    private fun Flip(position:Int){
              if(memoryGame.isWon()){
                  timetaken.stop()
                  val st=SystemClock.elapsedRealtime()-timetaken.base
                Snackbar.make(Clayout,"YOU WON",Snackbar.LENGTH_LONG).show()
                  return
              }
        if(memoryGame.isUp(position)){
            Snackbar.make(Clayout,"INVALID MOVE",Snackbar.LENGTH_SHORT).show()
            return
        }
            if( memoryGame.flipCard(position)){
             pairs.text="Pairs:${memoryGame.pairs}/${boardSize.NumPairs()}"
                if(memoryGame.isWon()){
                    timetaken.stop()
                    val st=SystemClock.elapsedRealtime()-timetaken.base
                    Snackbar.make(Clayout,"YOU WON",Snackbar.LENGTH_LONG).show()
                }
            }
              adapter.notifyDataSetChanged()
    }



}