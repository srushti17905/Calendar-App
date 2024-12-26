package com.example.calendar_app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calendar_app.ui.theme.Calendar_AppTheme

class Edit_Page : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val id = intent.getIntExtra("id", -1)
            val title = intent.getStringExtra("title")
            val detail = intent.getStringExtra("detail")

            val editTitle = mutableStateOf("$title")
            val editDescription = mutableStateOf("$detail")

            val deletNote = mutableStateOf(false)

            Calendar_AppTheme {

                if (deletNote.value) {

                    AlertDialog(
                        onDismissRequest = { deletNote.value = false },
                        containerColor = star.lpink,
                        confirmButton = {

                            Button(
                                onClick = {

                                    val intent = Intent(this@Edit_Page, MainActivity::class.java)

                                    val myDatabase = Database(applicationContext)
                                    myDatabase.deleteData(id = id)

                                    startActivity(intent)
                                    finish()

                                },
                                colors = ButtonDefaults.buttonColors(containerColor = star.cream)
                            ) {
                                Text(
                                    text = "Yes",
                                    fontSize = 13.sp,
                                    color = star.bcolor,
                                    fontFamily = FontFamily(Font(R.font.sun))
                                )
                            }

                        },
                        dismissButton = {

                            Button(
                                onClick = { deletNote.value = false },
                                colors = ButtonDefaults.buttonColors(containerColor = star.cream)
                            ) {
                                Text(
                                    text = "No",
                                    fontSize = 13.sp,
                                    color = star.bcolor,
                                    fontFamily = FontFamily(Font(R.font.sun))
                                )

                            }

                        },
                        text = {
                            Text(
                                text = "Delete this Event ?",
                                fontSize = 20.sp,
                                color = Color.Black
                            )
                        })

                }

                Scaffold(topBar = {
                    TopAppBar(
                        {
                            Text(
                                text = " Edit & Delete",
                                fontSize = 25.sp,
                                color = Color.Black,
                                fontFamily = FontFamily(
                                    Font(R.font.star)
                                )
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(star.lpink),
                        navigationIcon = {
                            Icon(
                                Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier
                                    .height(30.dp)
                                    .width(30.dp)
                                    .clickable {

                                        val intent =
                                            Intent(this@Edit_Page, MainActivity::class.java)
                                        startActivity(intent)
                                        finish()

                                    }
                            )
                        },
                        actions = {

                            Image(
                                painter = painterResource(R.drawable.edit),
                                contentDescription = null,
                                modifier = Modifier
                                    .height(70.dp)
                                    .padding(15.dp)
                            )
                        }
                    )
                }) { paddingValues ->

                    Column(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                            .background(color = star.cream)
                    ) {

                        Spacer(
                            modifier = Modifier
                                .height(30.dp)
                                .fillMaxWidth()
                        )

                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.Top,
                            modifier = Modifier
                                .height(30.dp)
                                .fillMaxWidth()
                        ) {

                            Text(
                                text = "     Edit Date",
                                fontSize = 15.sp,
                                color = Color.Black,
                                fontFamily = FontFamily(
                                    Font(R.font.sun)
                                )
                            )

                        }

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.Top,
                            modifier = Modifier
                                .height(120.dp)
                                .fillMaxWidth()
                        ) {
                            TextField(
                                value =  editTitle.value,
                                onValueChange = { editTitle.value = it },
                                textStyle = TextStyle(
                                    fontSize = 15.sp,
                                    fontFamily = FontFamily(Font(R.font.star)) , fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier
                                    .height(90.dp)
                                    .shadow(elevation = 20.dp)
                                    .width(330.dp),
                                shape = RoundedCornerShape(10.dp),
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = star.lBlue,
                                    focusedContainerColor = Color.White,
                                    unfocusedIndicatorColor = star.lBlue,
                                    unfocusedTextColor = Color.Black,
                                    focusedTextColor = Color.Black,
                                    focusedIndicatorColor = Color.White
                                )
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .height(40.dp)
                                .fillMaxWidth()
                        ) {

                            Text(
                                text = "     Edit Event",
                                fontSize = 15.sp,
                                color = Color.Black,
                                fontFamily = FontFamily(
                                    Font(R.font.sun)
                                )
                            )

                        }

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .height(300.dp)
                                .fillMaxWidth()
                        ) {

                            TextField(
                                value = editDescription.value,
                                onValueChange = { editDescription.value = it },
                                textStyle = TextStyle(
                                    fontSize = 15.sp,
                                    fontFamily = FontFamily(Font(R.font.star))
                                ),
                                modifier = Modifier
                                    .height(300.dp)
                                    .shadow(elevation = 20.dp)
                                    .width(330.dp),
                                shape = RoundedCornerShape(10.dp),
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = star.lBlue,
                                    focusedContainerColor = Color.White,
                                    unfocusedIndicatorColor = star.lBlue,
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                    focusedIndicatorColor = Color.White
                                )
                            )

                        }

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.Bottom,
                            modifier = Modifier
                                .height(230.dp)
                                .fillMaxWidth()
                        ) {

                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .height(230.dp)
                                    .width(180.dp)
                            ) {

                                ElevatedButton(
                                    onClick = {

                                        val intent =
                                            Intent(this@Edit_Page, MainActivity::class.java)

                                        val myDatabase = Database(applicationContext)

                                        myDatabase.updateData(
                                            id = id,
                                            newTitle = editTitle.value,
                                            newDetail = editDescription.value
                                        )

                                        startActivity(intent)

                                    },
                                    border = BorderStroke(2.dp, color = star.border),
                                    modifier = Modifier
                                        .height(60.dp)
                                        .width(100.dp),
                                    colors = ButtonDefaults.elevatedButtonColors(containerColor = star.buttonColor)
                                ) {
                                    Text(
                                        text = "Save",
                                        fontSize = 15.sp,
                                        color = Color.Black,
                                        fontFamily = FontFamily(
                                            Font(R.font.star)
                                        )
                                    )
                                }

                            }

                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .height(230.dp)
                                    .width(180.dp)
                            ) {
                                ElevatedButton(
                                    onClick = {

                                        deletNote.value = true

                                    },
                                    border = BorderStroke(2.dp, color = star.border),
                                    modifier = Modifier
                                        .height(60.dp)
                                        .width(100.dp),
                                    colors = ButtonDefaults.elevatedButtonColors(containerColor = star.buttonColor)
                                ) {
                                    Text(
                                        text = "Delete",
                                        fontSize = 15.sp,
                                        color = Color.Black,
                                        fontFamily = FontFamily(
                                            Font(R.font.star)
                                        )
                                    )
                                }
                            }

                        }

                    }

                }


            }

        }
    }
}

