package com.example.calendar_app

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Check
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
import com.example.calendar_app.MainActivity.Companion.date
import com.example.calendar_app.MainActivity.Companion.month
import com.example.calendar_app.ui.theme.Calendar_AppTheme

@Suppress("DEPRECATION")
class AddPage : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val title = mutableStateOf("")
            val task = mutableStateOf("")

            val colorList = ArrayList<Color>()

            colorList.add(Color(0xFFCCFFFF))
            colorList.add(Color(0xFFCCFFCC))
            colorList.add(Color(0xFFCCCCFF))
            colorList.add(Color(0xFFFFCCCC))
            colorList.add(Color(0xFF8E8EC5))
            colorList.add(Color(0xFFCCFFFF))
            colorList.add(Color(0xFFCCFFFF))
            colorList.add(Color(0xFFCCFFFF))

            Calendar_AppTheme {

                Scaffold(topBar = {
                    TopAppBar(
                        {
                            Text(
                                text = " Add Event",
                                fontSize = 30.sp, fontWeight = FontWeight.W100,
                                color = Color.Black, fontFamily = FontFamily(Font(R.font.star))
                            )
                        },
                        navigationIcon = {
                            Icon(
                                Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier
                                    .height(30.dp)
                                    .width(30.dp)
                                    .clickable {

                                        val intent = Intent(this@AddPage, MainActivity::class.java)
                                        startActivity(intent)
                                        finish()

                                    }
                            )
                        },
                        actions = {

                            Icon(
                                Icons.Outlined.Check,
                                contentDescription = null,
                                Modifier
                                    .height(30.dp)
                                    .clickable {

                                        if (title.value != "" && task.value != "") {

                                            val intent =
                                                Intent(this@AddPage, MainActivity::class.java)

                                            startActivity(intent)
                                            finish()

                                        } else {

                                            Toast
                                                .makeText(
                                                    this@AddPage,
                                                    "Add Your Event For Save",
                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()

                                        }

                                    }
                                    .width(40.dp), tint = Color.Black
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = star.lpink)
                    )
                },
                    snackbarHost = {
                        Text(
                            text = "Manage your Day ",
                            fontSize = 20.sp,
                            color = Color.Black, fontFamily = FontFamily(Font(R.font.title))
                        )
                    }) { paddingValues ->

                    Column(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                            .background(star.cream)
                    ) {

                        Row(
                            modifier = Modifier
                                .height(20.dp)
                                .fillMaxWidth()
                        ) {

                        }

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .height(140.dp)
                                .fillMaxWidth()
                        ) {
                            TextField(
                                value = title.value,
                                onValueChange = { title.value = it },
                                prefix = {
                                    Text(
                                        text = "Date : ",
                                        fontSize = 18.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.SemiBold,
                                        fontFamily = FontFamily(Font(R.font.star))
                                    )
                                },
                                textStyle = TextStyle(
                                    fontSize = 15.sp, fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily(Font(R.font.star))
                                ),
                                modifier = Modifier
                                    .height(90.dp)
                                    .shadow(elevation = 20.dp)
                                    .width(330.dp),
                                shape = RoundedCornerShape(10.dp),
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = star.lBlue,
                                    focusedContainerColor = star.light,
                                    unfocusedIndicatorColor = star.lBlue,
                                    unfocusedTextColor = Color.Black,
                                    focusedTextColor = Color.Black,
                                    focusedIndicatorColor = Color.White
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
                                value = task.value,
                                onValueChange = { task.value = it },
                                prefix = {
                                    Text(
                                        text = "Event : ",
                                        fontSize = 18.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.SemiBold,
                                        fontFamily = FontFamily(Font(R.font.star))
                                    )
                                },
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
                                    focusedContainerColor = star.light,
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
                            Image(
                                painter = painterResource(R.drawable.task1),
                                contentDescription = null
                            )

                        }

                    }

                }

            }

        }
    }
}


