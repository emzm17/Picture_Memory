package com.example.picturememory

enum class BoardSize(val cards:Int) {
    Easy(8),
    Meduim(18),
    Hard(24);


    fun width():Int{
        return when(this){
            Easy -> 2
            Meduim -> 3
            Hard -> 4
        }
    }

    fun height():Int{
        return cards/width()
    }

    fun NumPairs():Int{
        return cards/2
    }

}