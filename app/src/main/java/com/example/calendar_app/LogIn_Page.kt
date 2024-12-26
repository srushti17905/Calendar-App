package com.example.calendar_app

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calendar_app.ui.theme.Calendar_AppTheme

class LogIn_Page : ComponentActivity() {

    @SuppressLint("UnrememberedMutableState", "CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {

            val username = mutableStateOf("")
            val password = mutableStateOf("")

            Calendar_AppTheme {

                val sharedPreferences = getSharedPreferences("user_prefs" , Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                editor.putBoolean("is_logged_in" , true)
                editor.putString("username" , username.value)

                editor.apply()

                Surface(modifier = Modifier.fillMaxSize()) {

                    Image(
                        painter = painterResource(R.drawable.back),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds, modifier = Modifier.alpha(0.7f)
                    )

                    Column(modifier = Modifier.fillMaxSize()) {

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.Bottom,
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth()
                        ) {
                            Text(text = "Log In", fontSize = 50.sp, color = Color.White)
                        }

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.Top,
                            modifier = Modifier
                                .height(100.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Enter Your Account",
                                fontSize = 15.sp,
                                color = Color.Black
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .height(100.dp)
                                .fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = username.value,
                                onValueChange = { username.value = it },
                                leadingIcon = {
                                    Image(
                                        painter = painterResource(R.drawable.user1),
                                        contentDescription = null,
                                        modifier = Modifier.height(30.dp)
                                    )
                                },
                                label = { Text(text = "Username", fontSize = 20.sp) },
                                modifier = Modifier
                                    .height(70.dp)
                                    .width(320.dp),
                                textStyle = TextStyle(fontSize = 20.sp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = star.bcolor,
                                    focusedTextColor = Color.White,
                                    focusedLabelColor = Color.Black,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedTextColor = Color.White, cursorColor = Color.White
                                ),
                                shape = RoundedCornerShape(10.dp), minLines = 1, maxLines = 1
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .height(100.dp)
                                .fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = password.value,
                                onValueChange = { password.value = it },
                                leadingIcon = {
                                    Image(
                                        painter = painterResource(R.drawable.lock),
                                        contentDescription = null,
                                        modifier = Modifier.height(25.dp)
                                    )
                                },
                                label = { Text(text = "Password", fontSize = 20.sp) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                modifier = Modifier
                                    .height(70.dp)
                                    .width(320.dp),
                                textStyle = TextStyle(fontSize = 20.sp),
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = star.bcolor,
                                    focusedTextColor = Color.White,
                                    focusedLabelColor = Color.Black,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedTextColor = Color.White, cursorColor = Color.White
                                ),
                                shape = RoundedCornerShape(10.dp), minLines = 1, maxLines = 1
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.Bottom,
                            modifier = Modifier
                                .height(100.dp)
                                .fillMaxWidth()
                        ) {
                            OutlinedButton(
                                onClick = {

                                    val myDatabase = Database(applicationContext)
                                    val cursor = myDatabase.SelectData(
                                        name = username.value,
                                        pass = password.value
                                    )

                                    if (cursor.moveToNext()) {

                                        if (username.value != "" && password.value != "") {

                                            val intent =
                                                Intent(
                                                    this@LogIn_Page,
                                                    MainActivity::class.java
                                                )
                                            intent.putExtra("name" , cursor.getString(1))
                                            intent.putExtra("email" , cursor.getString(2))
                                            intent.putExtra("number" , cursor.getString(3))
                                            intent.putExtra("pass" , cursor.getString(4))

                                            startActivity(intent)

                                            finish()
                                        }
                                    }


                                },
                                modifier = Modifier
                                    .height(60.dp)
                                    .alpha(0.6f)
                                    .width(130.dp),
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(2.dp, color = Color.White)
                            ) {
                                Text(text = "LogIn", fontSize = 25.sp, color = Color.Black)
                            }
                        }

                        Spacer(modifier = Modifier.height(160.dp))

                        Row(
                            modifier = Modifier
                                .height(30.dp)
                                .fillMaxWidth()
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .height(30.dp)
                                    .width(230.dp)
                            ) {
                                Text(
                                    text = "don't have an account ? ",
                                    fontSize = 20.sp,
                                    color = Color.Black
                                )
                            }

                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .height(30.dp)
                                    .alpha(0.6f)
                                    .width(100.dp)
                            ) {
                                Text(
                                    text = "Sign Up",
                                    fontSize = 20.sp,
                                    color = Color.White, modifier = Modifier.clickable {

                                        val intent =
                                            Intent(this@LogIn_Page, Sign_Up::class.java)
                                        startActivity(intent)

                                    }
                                )
                            }


                        }

                    }

                }
            }

        }
    }

}

