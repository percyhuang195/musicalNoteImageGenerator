package com.example.notegen

import android.util.Log
import android.util.Log.i
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.abs

data class noteObject(
    var note : Int = 1,
    var octHighOrLow : Int = 0,
    var halfHighOrLow : Int = 0,
)

@Composable
fun noteGenerator(size: Int,noteList: List<noteObject>){
    var lines by remember { mutableStateOf(5) }
    var sizeNumber by remember { mutableStateOf((size / 70)) }
    var noteLeft = remember { mutableStateListOf<Boolean>() }
    var displayList : MutableList<noteObject> = mutableListOf()
    Box(
        modifier = Modifier
            .width(size.dp)
            .height(size.dp)
            .background(
                color = Color(0xFFFFFFFF)
            ),
        contentAlignment = Alignment.TopCenter
    ){
        noteLeft.clear()
        displayList = noteList.toMutableList()
        displayList.sortedBy { it.note }
        var removeList = mutableListOf<Int>()
        for (i in 0 until displayList.count()){
            for (j in 0 until displayList.count()){
                if (displayList[i].note == displayList[j].note){
                    removeList.add(i)
                }
            }
        }
        for (i in removeList.count() until 0){
            displayList.removeAt(removeList[i])
        }
        for (i in 0 until displayList.count()){
            if (i == 0){
                noteLeft.add(false)
            }else if (noteLeft[i - 1]){
                noteLeft.add(false)
            }else {
                for (j in 0 until i){
                    if (noteLeft.count() <= i){
                        if (abs(displayList[i].note - displayList[j].note) == 1){
                            noteLeft.add(true)
                        }
                    }
                }
                if (noteLeft.count() <= i){
                    noteLeft.add(false)
                }
            }
        }
//        Log.d("percy",removeList.toString())
        Log.d("percy",displayList.count().toString())
        for (i in 0 until displayList.count()){
            Box(
                Modifier
                    .offset(
                        (if (noteLeft[i]) size / 8 - 5 else 0).dp,
                        ((size / 8) * (14 - displayList[i].note) * 0.5 + 2).dp
                    )
                    .size((size / 8).dp)
                    .background(
                        color = Color.Black,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ){
                Box(
                    Modifier
                        .size((size / 8 - 5).dp)
                        .background(
                            color = Color.White,
                            shape = CircleShape
                        ),)
            }
            if (displayList[i].note == 1){
                Box(
                    modifier = Modifier
                        .offset(
                            0.dp,
                            ((size / 8) * 7).dp
                        )
                        .fillMaxWidth(0.2f)
                        .height(sizeNumber.dp)
                        .background(
                            color = Color(0xFF000000)
                        )
                )
            }
            if (displayList[i].note >= 13){
                Box(
                    modifier = Modifier
                        .offset(
                            0.dp,
                            ((size / 8)).dp
                        )
                        .fillMaxWidth(0.2f)
                        .height(sizeNumber.dp)
                        .background(
                            color = Color(0xFF000000)
                        )
                )
            }
        }
        Column(Modifier.fillMaxSize()) {
            Spacer(
                Modifier.height((size / 8).dp)
            )
            Spacer(
                Modifier.height((size / 8).dp)
            )
            LazyColumn(
            ) {
                items(lines){
                    Column (
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .height(sizeNumber.dp)
                                .background(
                                    color = Color(0xFF000000)
                                )
                        )
                        Spacer(
                            Modifier.height((size / 8 - sizeNumber).dp)
                        )
                    }
                }
            }
        }
    }
}
