package com.example.calendar_app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calendar_app.My_Calendar.Companion.event
import com.example.calendar_app.My_Calendar.Companion.show
import com.example.calendar_app.My_Calendar.Companion.showBox
import com.example.calendar_app.ui.theme.Calendar_AppTheme
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Date
import java.util.Locale

class My_Calendar : ComponentActivity() {

    companion object {

        val date = mutableStateOf("")
        val event = mutableStateOf("")
        val showBox = mutableStateOf(false)
        val show = mutableStateOf(false)

        var showDate = ""
        var showMonth = ""
        var showYear = ""
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
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
                        actions = {

                            Button(
                                onClick = {

                                    if (event.value != "") {

                                        val intent =
                                            Intent(this@My_Calendar, MainActivity::class.java)

                                        val myDatabase = Database(applicationContext)

                                        myDatabase.insertShow(date = "${showDate}-${showMonth}-${showYear}", month = showMonth , year = showYear , title = showDate , detail = event.value)

                                        show.value = false
                                        showBox.value = false

                                        date.value = ""
                                        event.value  = ""

                                        startActivity(intent)
                                        finish()

                                    } else {

                                        Toast.makeText(
                                            this@My_Calendar,
                                            "fill the detail for save",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    }

                                },
                                colors = ButtonDefaults.buttonColors(star.cream),
                                border = BorderStroke(9.dp, color = star.lpink),
                                modifier = Modifier
                                    .padding(5.dp)
                                    .height(55.dp),
                                enabled = if (show.value || showBox.value) {
                                    true
                                } else {
                                    false
                                }
                            )
                            {
                                Icon(
                                    Icons.Outlined.Check,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .height(30.dp)
                                        .width(20.dp)
                                )
                            }

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


                                        val intent =
                                            Intent(this@My_Calendar, MainActivity::class.java)

                                        show.value = false
                                        showBox.value = false

                                        date.value = ""
                                        event.value = ""

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
                        Spacer(modifier = Modifier.height(20.dp))

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

                                // todo(calendar start)

                                val currentMonth = remember {

                                    YearMonth.now()
                                }

                                val dayInMonth = remember(currentMonth) {

                                    Calendar.getInstance().apply {

                                        time = Date()

                                        set(Calendar.DAY_OF_MONTH, currentMonth.monthValue - 1)
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
                                            fontSize = 20.sp,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Medium,
                                            fontFamily = FontFamily(Font(R.font.star))
                                        )
                                    }



                                    LazyRow {

                                        items(
                                            DayOfWeek.values()
                                                .sortedBy { it.value % 7 }) { DayofWeek ->

                                            Text(
                                                text = DayofWeek.getDisplayName(
                                                    TextStyle.SHORT,
                                                    Locale.UK
                                                ),
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = star.red,
                                                modifier = Modifier
                                                    .width(48.dp)
                                                    .padding(3.dp),
                                                fontFamily = FontFamily(Font(R.font.star))
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))

                                    LazyVerticalGrid(columns = GridCells.Fixed(7)) {

                                        items(dayInMonth) { day ->

                                            Surface(
                                                modifier = Modifier
                                                    .height(50.dp)
                                                    .padding(
                                                        top = 3.dp,
                                                        bottom = 1.dp,
                                                        start = 1.dp,
                                                        end = 1.dp
                                                    )
                                                    .width(30.dp),
                                                shape = RoundedCornerShape(40.dp),
                                                color = if (MainActivity.date == day + 1) {
                                                    star.lBlue
                                                } else {
                                                    star.box
                                                }
                                            ) {

                                                Box(
                                                    modifier = Modifier.fillMaxSize(),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Text(
                                                        text = (day + 1).toString(),
                                                        fontSize = 18.sp,
                                                        color = if (MainActivity.date == day + 1) {
                                                            star.red
                                                        }
                                                        else {
                                                            Color.DarkGray
                                                        },
                                                        fontWeight = FontWeight.W100,
                                                        modifier = Modifier
                                                            .clickable {

                                                                showBox.value = true

                                                                Log.d("==>", "onCreate: ${day + 1}")

                                                                showDate = "${day + 1}"
                                                                showMonth = "${currentMonth.month}"
                                                                showYear = "${currentMonth.year}"

                                                                Log.d(
                                                                    "===>",
                                                                    "onCreate: ${showDate}"
                                                                )
                                                            }
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                                // todo(finish)
                            }

                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth()
                        ) {

                            // todo(edit box)

                            addBox()
                        }

                        Row(
                            modifier = Modifier
                                .height(70.dp)
                                .fillMaxWidth()
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .height(70.dp)
                                    .width(70.dp)
                            ) {
                            }

                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .height(70.dp)
                                    .width(200.dp)
                            ) {
                                Text(
                                    text = "What’s your plan for today?",
                                    fontSize = 16.sp,
                                    color = star.bcolor,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontFamily = FontFamily(Font(R.font.title))
                                )
                            }

                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .height(70.dp)
                                    .width(100.dp)
                            ) {

                                Icon(
                                    painter = painterResource(R.drawable.add),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .height(40.dp)
                                        .clickable {

                                            show.value = true

                                            showDate = "${MainActivity.date}"
                                            showMonth = MainActivity.month
                                            showYear = "${MainActivity.year}"

                                        }
                                        .width(40.dp),
                                    tint = Color.Black
                                )

                            }
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun addBox() {

    if (show.value || showBox.value) {

        Surface(
            modifier = Modifier
                .height(190.dp)
                .padding(top = 10.dp)
                .width(320.dp),
            shape = RoundedCornerShape(10.dp),
            color = star.box, shadowElevation = 10.dp
        )
        {
            Column(modifier = Modifier.fillMaxSize()) {

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
                            .width(215.dp)

                    ) {

                        Text(
                            text = "Add Your Event",
                            fontSize = 15.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.W100,
                            fontFamily = FontFamily(Font(R.font.star))
                        )

                    }

                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .height(30.dp)
                            .width(85.dp)

                    ) {

                        Image(
                            painter = painterResource(R.drawable.close),
                            contentDescription = null,
                            modifier = Modifier
                                .height(10.dp)
                                .clickable {

                                    show.value = false
                                    showBox.value = false

                                }
                        )

                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth()
                ) {

                    TextField(
                        value = "${My_Calendar.showDate} - ${My_Calendar.showMonth} - ${My_Calendar.showYear}",
                        onValueChange = {  },
                        leadingIcon = {
                            Image(
                                painter = painterResource(R.drawable.calendar),
                                contentDescription = null,
                                modifier = Modifier.height(20.dp)
                            )
                        },
                        prefix = {

                            Text(
                                text = ": ",
                                fontSize = 30.sp,
                                color = Color.Black
                            )

                        },
                        modifier = Modifier
                            .height(60.dp)
                            .width(300.dp),
                        shape = RoundedCornerShape(12.dp),
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontSize = 15.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.sun))
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = star.light,
                            unfocusedContainerColor = star.lBlue,
                            focusedIndicatorColor = Color.White,
                            unfocusedIndicatorColor = star.lpink
                        )
                    )
                }

                Spacer(modifier = Modifier.height(7.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .height(80.dp)
                        .fillMaxWidth()
                ) {
                    TextField(
                        value = event.value,
                        onValueChange = { event.value = it },
                        leadingIcon = {
                            Image(
                                painter = painterResource(R.drawable.event),
                                contentDescription = null,
                                modifier = Modifier.height(20.dp)
                            )
                        },
                        modifier = Modifier
                            .height(60.dp)
                            .width(300.dp),
                        shape = RoundedCornerShape(12.dp),
                        prefix = {
                            Text(
                                text = ": ",
                                fontSize = 30.sp,
                                color = Color.Black
                            )
                        },
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontSize = 15.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.star))
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = star.light,
                            unfocusedContainerColor = star.lBlue,
                            focusedIndicatorColor = Color.White,
                            unfocusedIndicatorColor = star.lBlue
                        )
                    )

                }
            }
        }
    }
}