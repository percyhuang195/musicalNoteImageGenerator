package com.example.notegen

import android.health.connect.datatypes.ExercisePerformanceGoal
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.LocalPinnableContainer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.IconCompat
import com.example.notegen.ui.theme.NoteGenTheme
import kotlin.collections.mutableListOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            var noteList = remember { mutableStateListOf<noteObject>(noteObject(1,0,0),noteObject(5,0,0)) }
            var noteNameList = listOf("Do","Re","Mi","Fa","Sol","La","Si")

            NoteGenTheme {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize().background(
                        color = Color(0xFFF1F1F1)
                    ),
                ) {
                    Spacer(Modifier.height(40.dp))
                    noteGenerator(300,noteList)
                    Spacer(Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        Arrangement.SpaceBetween,
                        Alignment.CenterVertically
                    ) {
                        Spacer(Modifier.width(20.dp))
                        Text("音符清單",
                            fontSize = 22.sp
                        )
                        IconButton(
                            onClick = {
                                noteList.add(noteObject(8,0,0))
                            }
                        ) {
                            Image(
                                Icons.Default.Add,
                                contentDescription = ""
                            )
                        }
                    }
                    Spacer(Modifier.height(20.dp))
                    LazyColumn(
                        Modifier.fillMaxHeight(0.8f)
                    ) {
                        items(noteList.count()){ index:Int ->
                            Column() {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.8f)
                                        .height(100.dp)
                                        .background(
                                            color = Color.White,
                                            shape = RoundedCornerShape(20.dp)
                                        ),
                                    contentAlignment = Alignment.CenterStart
                                ){
                                    Row(
                                        Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column() {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Spacer(Modifier.width(20.dp))
                                                Box(
                                                    modifier = Modifier.width(30.dp)
                                                ){
                                                    Text(noteNameList[(noteList[index].note - 1) % 7])
                                                }
                                                Spacer(Modifier.weight(0.25f))
                                                Text("b")
                                                Spacer(Modifier.weight(0.05f))
                                                Row(
                                                    modifier = Modifier.weight(0.4f),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Slider(
                                                        value = noteList[index].halfHighOrLow.toFloat(),
                                                        valueRange = -1f..1f,
                                                        steps = 1,
                                                        onValueChange = {
                                                            noteList[index] = noteList[index].copy(halfHighOrLow = it.toInt())
                                                        }
                                                    )
                                                }
                                                Spacer(Modifier.weight(0.05f))
                                                Text("#")
                                                Spacer(Modifier.weight(0.25f))
                                                IconButton(
                                                    onClick = {
                                                        noteList.removeAt(index)
                                                    }
                                                ) {
                                                    Image(
                                                        Icons.Default.Delete,
                                                        contentDescription = ""
                                                    )
                                                }
                                            }
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth(0.95f)
                                                    .height(40.dp),
                                                contentAlignment = Alignment.Center
                                            ){
                                                Row(
                                                    Modifier.fillMaxWidth()
                                                ) {
                                                    Spacer(Modifier.width(10.dp))
                                                    Slider(
                                                        valueRange = 1f..14f,
                                                        steps = 12,
                                                        value = noteList[index].note.toFloat(),
                                                        onValueChange = {
                                                            noteList[index] = noteList[index].copy(note = it.toInt())
                                                        }
                                                    )
                                                    Spacer(Modifier.width(10.dp))
                                                }
                                            }
                                        }
                                    }
                                }
                                Spacer(Modifier.height(10.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}