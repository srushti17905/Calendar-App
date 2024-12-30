package com.example.calendar_app

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calendar_app.ui.theme.Calendar_AppTheme
import kotlin.properties.Delegates

class Splash_Screen : ComponentActivity() {

    companion object {

        lateinit var sharedPreferences: SharedPreferences
        lateinit var editor: Editor

    }

    @SuppressLint("RememberReturnType", "CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

        enableEdgeToEdge()
        setContent {

            Calendar_AppTheme {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(star.splash)
                ) {
                    Image(
                        painter = painterResource(R.drawable.shplash),
                        contentDescription = null,
                        modifier = Modifier
                            .height(250.dp)
                            .width(250.dp)
                    )
                }

                Handler(Looper.getMainLooper()).postDelayed({

                    if (isLoggedIn) {
                        startActivity(Intent(this@Splash_Screen, MainActivity::class.java))
                        return@postDelayed
                    }
                    else {
                        startActivity(Intent(this@Splash_Screen, LogIn_Page::class.java))
                    }

                    finish()

                }, 2000)

            }
        }
    }
}
