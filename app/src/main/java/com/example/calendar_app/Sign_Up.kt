package com.example.calendar_app

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calendar_app.ui.theme.Calendar_AppTheme

class Sign_Up : ComponentActivity() {

    companion object {


        val name = mutableStateOf("")
        val email = mutableStateOf("")
        val phone = mutableStateOf("")
        val pass = mutableStateOf("")

        val setError = mutableStateOf(false)

    }

    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            Calendar_AppTheme {

                Surface(modifier = Modifier.fillMaxSize()) {

                    Image(
                        painter = painterResource(R.drawable.back),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds, modifier = Modifier.alpha(0.7f)
                    )

                    Column(modifier = Modifier.fillMaxSize()) {

                        Row(
                            modifier = Modifier
                                .height(70.dp)
                                .fillMaxWidth()
                        ) {

                        }

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.Bottom,
                            modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth()
                        ) {
                            Text(text = "Sign Up", fontSize = 50.sp, color = Color.Black)
                        }

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.Top,
                            modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth()
                        ) {

                            Text(
                                text = "Enter Your Credential",
                                fontSize = 16.sp,
                                color = Color.White
                            )

                        }
                        Row(
                            modifier = Modifier
                                .height(10.dp)
                                .fillMaxWidth()
                        ) {

                        }

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .height(100.dp)
                                .fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = name.value,
                                onValueChange = { name.value = it },
                                leadingIcon = {
                                    Image(
                                        painter = painterResource(R.drawable.user1),
                                        contentDescription = null,
                                        modifier = Modifier.height(30.dp)
                                    )
                                },
                                label = { Text(text = "Username", fontSize = 15.sp) },
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
                                value = email.value,
                                onValueChange = { email.value = it },
                                leadingIcon = {

                                    Image(
                                        painter = painterResource(R.drawable.email),
                                        contentDescription = null,
                                        modifier = Modifier.height(20.dp)
                                    )

                                },
                                label = { Text(text = "Email", fontSize = 15.sp) },
                                isError = if (!email.value.contains("@gmail.com") && email.value.isNotEmpty()) {
                                    true
                                } else {
                                    false
                                },
                                modifier = Modifier
                                    .height(70.dp)
                                    .width(320.dp),
                                textStyle = TextStyle(fontSize = 20.sp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = star.bcolor,
                                    focusedTextColor = Color.White,
                                    errorCursorColor = star.red,
                                    errorIndicatorColor = star.red,
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
                                value = phone.value,
                                onValueChange = { phone.value = it },
                                leadingIcon = {

                                    Image(
                                        painter = painterResource(R.drawable.phone),
                                        contentDescription = null, modifier = Modifier.height(20.dp)
                                    )

                                },
                                isError = if (phone.value.isNotEmpty() && phone.value.length != 10) {
                                    true
                                } else {
                                    setError.value
                                },
                                label = { Text(text = "Phone Number", fontSize = 15.sp) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier
                                    .height(70.dp)
                                    .width(320.dp),
                                textStyle = TextStyle(fontSize = 20.sp),
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = star.bcolor,
                                    focusedTextColor = Color.White,
                                    focusedLabelColor = Color.Black,
                                    errorIndicatorColor = star.red,
                                    errorContainerColor = Color.Transparent,
                                    errorCursorColor = star.red,
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
                                value = pass.value,
                                onValueChange = { pass.value = it },
                                leadingIcon = {

                                    Image(
                                        painter = painterResource(R.drawable.lock),
                                        contentDescription = null,
                                        modifier = Modifier.height(20.dp)
                                    )

                                },
                                label = { Text(text = "Password", fontSize = 15.sp) },
                                isError = if (pass.value.length != 6 && pass.value.isNotEmpty()) {
                                    true
                                } else {
                                    setError.value
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                modifier = Modifier
                                    .height(70.dp)
                                    .width(320.dp),
                                textStyle = TextStyle(fontSize = 20.sp),
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = star.bcolor,
                                    focusedTextColor = Color.White,
                                    focusedLabelColor = Color.Black,
                                    errorIndicatorColor = star.red,
                                    errorCursorColor = star.red,
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
                                .height(150.dp)
                                .fillMaxWidth()
                        ) {

                            OutlinedButton(
                                onClick = {



                                        if (name.value != "" && email.value != "" && phone.value != "" && pass.value != "") {

                                            val intent =
                                                Intent(this@Sign_Up, LogIn_Page::class.java)

                                            val myDatabase = Database(applicationContext)
                                            myDatabase.insertData(
                                                name = name.value,
                                                email = email.value,
                                                number = phone.value,
                                                pass = pass.value
                                            )

                                            startActivity(intent)
                                            finish()
                                        }



                                },
                                modifier = Modifier
                                    .height(55.dp)
                                    .alpha(0.7f)
                                    .width(130.dp),
                                border = BorderStroke(2.dp, color = Color.White),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(text = "Sign Up", fontSize = 25.sp, color = Color.Black)
                            }

                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier
                                .height(100.dp)
                                .fillMaxWidth()
                        ) {

                            Row(
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .height(100.dp)
                                    .alpha(0.7f)
                                    .width(240.dp)
                            ) {
                                Text(
                                    text = "already have an account? ",
                                    fontSize = 20.sp,
                                    color = Color.White
                                )
                            }

                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .height(100.dp)
                                    .width(100.dp)

                            ) {
                                Text(
                                    text = "Log In",
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    modifier = Modifier.clickable {

                                        val intent = Intent(this@Sign_Up, LogIn_Page::class.java)
                                        startActivity(intent)

                                    })
                            }

                        }

                    }

                }


            }
        }
    }
}


