package com.example.calendar_app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calendar_app.ui.theme.Calendar_AppTheme
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Date
import java.util.Locale

class Calendar : ComponentActivity() {


    @SuppressLint("UnrememberedMutableState")
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            var date = mutableStateOf("")
            var event = mutableStateOf("")

            Calendar_AppTheme {

                Scaffold(topBar = {

                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = "Calendar",
                                fontSize = 40.sp,
                                color = Color.Black, fontFamily = FontFamily(Font(R.font.title))
                            )
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = star.lpink),
                        navigationIcon = {

                            Icon(
                                Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = null,
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(25.dp)
                                    .clickable {

                                        val intent = Intent(this@Calendar, MainActivity::class.java)
                                        startActivity(intent)
                                        finish()

                                    }, tint = Color.Black
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
                        Spacer(modifier = Modifier.height(40.dp))

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .height(400.dp)
                                .fillMaxWidth()
                        ) {

                            Card(
                                modifier = Modifier
                                    .height(400.dp)
                                    .shadow(elevation = 30.dp)
                                    .width(330.dp),
                                colors = CardColors(
                                    containerColor = star.box,
                                    contentColor = Color.DarkGray,
                                    disabledContentColor = star.box,
                                    disabledContainerColor = star.box
                                )
                            ) {

                                val currentMonth = remember {
                                    YearMonth.now()
                                }

                                MonthlyCalendar(currentMonth = currentMonth)

                            }

                        }

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.Top,
                            modifier = Modifier
                                .height(190.dp)
                                .fillMaxWidth()
                        ) {

                            Surface(
                                modifier = Modifier
                                    .height(190.dp)
                                    .width(330.dp)
                                    .padding(top = 10.dp),
                                shape = RoundedCornerShape(10.dp),
                                color = star.box, shadowElevation = 10.dp
                            ) {

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {

                                    TextField(
                                        value = date.value,
                                        onValueChange = { date.value = it },
                                        label = {
                                            Text(
                                                text = "Date : ",
                                                fontSize = 15.sp,
                                                color = Color.Black
                                            )
                                        },
                                        modifier = Modifier
                                            .height(60.dp)
                                            .width(310.dp),
                                        textStyle = androidx.compose.ui.text.TextStyle(
                                            fontSize = 20.sp,
                                            color = Color.Black
                                        ),
                                        shape = RoundedCornerShape(10.dp),
                                        colors = TextFieldDefaults.colors(
                                            unfocusedContainerColor = star.lBlue,
                                            focusedContainerColor = star.cream,
                                            unfocusedIndicatorColor = star.lBlue,
                                            focusedIndicatorColor = star.cream
                                        ), minLines = 1, maxLines = 1
                                    )

                                    Spacer(modifier = Modifier.height(10.dp))

                                    TextField(
                                        value = event.value,
                                        onValueChange = { event.value = it },
                                        label = {
                                            Text(
                                                text = "Event : ",
                                                fontSize = 15.sp,
                                                color = Color.Black
                                            )
                                        },
                                        modifier = Modifier
                                            .height(90.dp)
                                            .width(310.dp),
                                        textStyle = androidx.compose.ui.text.TextStyle(
                                            fontSize = 20.sp,
                                            color = Color.Black
                                        ),
                                        shape = RoundedCornerShape(10.dp),
                                        colors = TextFieldDefaults.colors(
                                            unfocusedContainerColor = star.lBlue,
                                            focusedContainerColor = star.cream,
                                            unfocusedIndicatorColor = star.lBlue,
                                            focusedIndicatorColor = star.cream
                                        ), minLines = 1, maxLines = 1
                                    )
                                }

                            }

                        }

                        Row(modifier = Modifier.height(60.dp).fillMaxWidth()) {

                            Row(
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.Bottom,
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(240.dp)
                            ) {
                                Text(
                                    text = "Menage Your Day",
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    fontFamily = FontFamily(Font(R.font.title))
                                )
                            }

                            Row(
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(90.dp)
                            ) {

                                Button(
                                    onClick = {}, modifier = Modifier
                                        .height(20.dp)
                                        .width(20.dp)
                                ) {

                                }

                            }
                        }
                    }

                }

            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("RememberReturnType")
@Composable
fun MonthlyCalendar(currentMonth: YearMonth) {

    val dayInMonth = remember(currentMonth) {

        Calendar.getInstance().apply {

            time = Date()

            set(Calendar.MONTH, currentMonth.monthValue - 1)
            set(Calendar.YEAR, currentMonth.year)

        }.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    Column {

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(90.dp)
                .fillMaxWidth()
        ) {

            Text(
                text = "${currentMonth.month} ${currentMonth.year}",
                fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.Medium,
                fontFamily = FontFamily(
                    Font(R.font.star)
                )
            )
        }

        LazyRow {

            // todo (week day)

            items(DayOfWeek.values().sortedBy { it.value % 7 }) { DayOfWeek ->

                Text(
                    text = DayOfWeek.getDisplayName(TextStyle.SHORT, Locale.UK),
                    fontSize = 15.sp, fontWeight = FontWeight.Bold,
                    color = star.red, modifier = Modifier
                        .width(48.dp)
                        .padding(3.dp), fontFamily = FontFamily(Font(R.font.star))
                )


            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyVerticalGrid(columns = GridCells.Fixed(7)) {

            // todo(day date)

            items(dayInMonth) { day ->

                Surface(
                    modifier = Modifier
                        .height(50.dp)
                        .padding(top = 3.dp, bottom = 1.dp, start = 1.dp, end = 1.dp)
                        .width(30.dp),
                    color = if (MainActivity.date == day + 1) {

                        star.lBlue

                    } else {
                        star.box
                    },
                    shape = RoundedCornerShape(40.dp)
                ) {

                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = (day + 1).toString(), fontWeight = FontWeight.Medium,
                            color = if (MainActivity.date == day + 1) {

                                Color.Black

                            } else {
                                Color.DarkGray
                            },
                            modifier = Modifier.padding(15.dp)
                        )
                    }
                }
            }


        }

    }


}