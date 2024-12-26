package com.example.calendar_app

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calendar_app.ui.theme.Calendar_AppTheme

class UserInfo : ComponentActivity() {


    @SuppressLint("CommitPrefEdits")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            Calendar_AppTheme {

                Scaffold(topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "User Info",
                                fontSize = 25.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(
                                    Font(R.font.sun)
                                )
                            )
                        },
                        navigationIcon = {
                            Image(
                                painter = painterResource(R.drawable.info),
                                contentDescription = null,
                                modifier = Modifier
                                    .height(60.dp)
                                    .padding(9.dp)
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(star.lpink)
                    )
                }) { paddingValues ->

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(star.cream)
                            .padding(paddingValues)
                    ) {

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .height(70.dp)
                                .fillMaxWidth()
                        ) {

                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .height(70.dp)
                                    .width(80.dp)

                            ) {
                                Text(
                                    text = "  Name : ",
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily(
                                        Font(R.font.star)
                                    )
                                )
                            }

                            Box(
                                contentAlignment = Alignment.Center, modifier = Modifier
                                    .height(70.dp)
                                    .width(150.dp)
                            ) {

                                Text(
                                    text = "",
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    fontFamily = FontFamily(
                                        Font(R.font.star)
                                    )
                                )

                            }

                        }

                        Row(
                            modifier = Modifier
                                .height(70.dp)
                                .fillMaxWidth()
                        ) {


                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .height(70.dp)
                                    .width(80.dp)

                            ) {
                                Text(
                                    text = "  Email : ",
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily(
                                        Font(R.font.star)
                                    )
                                )
                            }

                            Box(
                                contentAlignment = Alignment.Center, modifier = Modifier
                                    .height(70.dp)
                                    .width(150.dp)
                            ) {

                                Text(
                                    text = "",
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    fontFamily = FontFamily(
                                        Font(R.font.star)
                                    )
                                )

                            }

                        }

                        Row(
                            modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth()
                        ) {


                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .height(70.dp)
                                    .width(110.dp)

                            ) {
                                Text(
                                    text = " Number : ",
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily(
                                        Font(R.font.star)
                                    )
                                )
                            }

                            Box(
                                contentAlignment = Alignment.Center, modifier = Modifier
                                    .height(70.dp)
                                    .width(150.dp)
                            ) {

                                Text(
                                    text = "",
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    fontFamily = FontFamily(
                                        Font(R.font.star)
                                    )
                                )

                            }

                        }

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth()
                        ) {

                            Button(
                                onClick = {

                                    LogIn_Page.sp.edit().clear().apply()
                                    Toast.makeText(this@UserInfo , "logout" , Toast.LENGTH_SHORT).show()

                                    val intent = Intent(this@UserInfo , LogIn_Page::class.java)
                                    startActivity(intent)

                                },
                                modifier = Modifier
                                    .height(60.dp)
                                    .width(100.dp),
                                shape = RoundedCornerShape(13.dp),
                                border = BorderStroke(2.dp, color = Color.DarkGray),
                                colors = ButtonColors(
                                    containerColor = star.lBlue,
                                    contentColor = star.lBlue,
                                    disabledContainerColor = star.lBlue,
                                    disabledContentColor = star.lBlue
                                )
                            ) {
                                Text(text = "Log Out", fontSize = 20.sp, color = Color.Black)
                            }

                        }


                    }

                }
            }
        }
    }
}
