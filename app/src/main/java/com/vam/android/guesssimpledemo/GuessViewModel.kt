package com.vam.android.guesssimpledemo

import android.graphics.Color
import android.view.View
import androidx.lifecycle.ViewModel

class GuessViewModel:ViewModel() {

    var colorList = mutableListOf<Int>(0,0,0,0)
    var guessList = mutableListOf<Int>()
    val colorMapping = intArrayOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.BLACK, Color.GRAY)

    fun resetNotRepeat(boolean: Boolean):List<Int>{
        val list = mutableListOf<Int>()
        if (boolean){
        for (i in 1..4){
            list+=(1..6).random()
        }
        }else{
            while(list.size<4){
                val temp = (1..6).random()
                if (!list.contains(temp)){
                    list+=temp
                }
            }
        }

        return list
    }

    fun direct(button: View, list:MutableList<Int>, num:Int){
        list[num] = list[num]%6+1
        button.setBackgroundColor(when (list[num]) {
            1 -> Color.RED
            2 -> Color.GREEN
            3 -> Color.BLUE
            4 -> Color.YELLOW
            5 -> Color.BLACK
            6 -> Color.GRAY
            else -> Color.WHITE
        })
    }

    fun direct2(list1: List<Int>, list2: List<Int>):Pair<Int,Int>{
        var fullpass = 0
        var templist1 = list1.toMutableList()
        var templist2 = list2.toMutableList()
        for (i in 0..3){
            if(list1[i]==list2[i]){
                var temp = list1[i]
                fullpass ++
                templist1-=temp
                templist2-=temp
            }
        }
        for (i in 0..templist1.size-1){
            templist1-=templist2[i]
        }
        val halfpass = templist2.size-templist1.size

        return Pair(fullpass,halfpass)
    }


    fun cheat(list: List<Int>):MutableList<String>{
        val cheatText = mutableListOf<String>()
        for (i in 0..3){
            cheatText.add(when (list[i]) {
                1 -> "红"
                2 -> "绿"
                3 -> "蓝"
                4 -> "黄"
                5 -> "黑"
                6 -> "灰"
                else -> "未知"
            })
        }
        return cheatText
    }
}
