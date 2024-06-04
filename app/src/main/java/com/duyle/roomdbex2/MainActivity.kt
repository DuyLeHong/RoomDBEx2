package com.duyle.roomdbex2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.duyle.roomdbex2.ui.theme.RoomDBEx2Theme

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoomDBEx2Theme {
                Scaffold(modifier = Modifier.fillMaxSize().safeDrawingPadding().padding(16.dp)) { innerPadding ->
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun HomeScreen () {
    val context = LocalContext.current

    val db = Room.databaseBuilder(
        context,
        StudentDB::class.java, "student-db"
    ).allowMainThreadQueries().build()

    var listStudents by remember {
        mutableStateOf(db.studentDAO().getAll())
    }

    Column (Modifier.fillMaxWidth()){
        Text(
            text = "Quan ly Sinh vien",
            style = MaterialTheme.typography.titleLarge
        )

        Button(onClick = {
            db.studentDAO().insert(StudentModel(hoten = "Tuan Tran", mssv = "PH13311", diemTB = 9.5f))

            listStudents = db.studentDAO().getAll()

        }) {
            Text(text = "Thêm SV")
        }

        LazyColumn {

            items(listStudents) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ){
                    Text(modifier = Modifier.weight(1f), text = it.uid.toString())
                    Text(modifier = Modifier.weight(1f), text = it.hoten.toString())
                    Text(modifier = Modifier.weight(1f), text = it.mssv.toString())
                    Text(modifier = Modifier.weight(1f), text = it.diemTB.toString())
                }
                Divider()
            }
        }
    }
}

