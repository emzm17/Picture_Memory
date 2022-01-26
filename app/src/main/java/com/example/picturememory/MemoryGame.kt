package com.example.picturememory



class MemoryGame(private val boardSize: BoardSize) {



    private var indexselectedcard:Int?=null
    var pairs=0
    private var numberFlips=0
    private var totalmoves=0
    val cards:List<Memory>
     init{
         val image= ConstantImage.icon.shuffled().take(boardSize.NumPairs())
         val rimage =(image+image).shuffled()
         cards=rimage.map{Memory(it)}
     }
    fun flipCard(position: Int) :Boolean{
        var flag=false
         val card = cards[position]
         if(indexselectedcard==null){
           restoreCard()
           indexselectedcard=position
         }
        else{
              flag = checkForMatch(indexselectedcard!!,position)
               indexselectedcard=null
         }
         card.up=!card.up
         numberFlips++
         totalmoves++
        return flag
    }

    private fun checkForMatch(p1: Int, p2: Int): Boolean {
        if(cards[p1].id!=cards[p2].id) {



            return false
        }
        else{
             cards[p1].matched=true
            cards[p2].matched=true
            pairs+=1




          return true

        }
    }

    private fun restoreCard() {
       for(i in cards) {
           if (!i.matched) {
               i.up = false
           }
       }
    }

  fun isWon(): Boolean {
         return pairs==boardSize.NumPairs()
    }

    fun isUp(position: Int): Boolean {
            return cards[position].up
    }

    fun getTotalMoves(): Int {
            return  numberFlips/2
    }
    fun getTotal():Int{
         return totalmoves
    }


}