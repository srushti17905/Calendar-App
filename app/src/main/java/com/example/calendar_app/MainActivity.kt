package com.example.calendar_app

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Space
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calendar_app.MainActivity.Companion.myDatabase
import com.example.calendar_app.ui.theme.Calendar_AppTheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import kotlin.math.log

class MainActivity : ComponentActivity() {

    object Share {

        @SuppressLint("QueryPermissionsNeeded")
        fun shareTextViaGmail(context: Context, subject: String, body: String) {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, body)
                putExtra(
                    Intent.EXTRA_EMAIL,
                    arrayOf("recipient@example.com")
                ) // Optional: Specify recipients
            }

            // Check if there's an activity that can handle the intent
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(Intent.createChooser(intent, "Share via"))
            } else {
                // Handle case where no email app is available
                Toast.makeText(context, "No email app found", Toast.LENGTH_SHORT).show()
            }
        }

        fun shareTextToWhatsApp(context: Context, text: String) {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
                `package` = "com.whatsapp" // Specify WhatsApp package
            }

            try {
                context.startActivity(sendIntent)
            } catch (e: ActivityNotFoundException) {
                // Handle the case where WhatsApp is not installed
                Toast.makeText(context, "WhatsApp is not installed", Toast.LENGTH_SHORT).show()
            }
        }

        fun copyTextToClipboard(context: Context, text: String) {
            val clipboard = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", text)
            clipboard.setPrimaryClip(clip)
        }
    }

    companion object {

        lateinit var myDatabase: Database
        lateinit var Data: Database
        val cal = Calendar.getInstance()

        // current date
        val date = cal.get(Calendar.DATE)
        var count = 0
        val list = listOf(
            "SUN",
            "MON",
            "THU",
            "WED",
            "THR",
            "FRI",
            "SAT",
            "SUN",
            "MON",
            "THU",
            "WED",
            "THR",
            "FRI",
            "SAT",
            "SUN",
            "MON",
            "THU",
            "WED",
            "THR",
            "FRI",
            "SAT",
            "SUN",
            "MON",
            "THU",
            "WED",
            "THR",
            "FRI",
            "SAT",
            "SUN",
            "MON",
            "THU",
            "WED",
            "THR",
            "FRI",
            "SAT"
        )
        val Month_List = listOf(
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
        )
        val month = Month_List[cal.get(Calendar.MONTH)]
        val year = cal.get(Calendar.YEAR)
        var selectDate = 0
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint(
        "UnrememberedMutableInteractionSource", "SimpleDateFormat",
        "UnrememberedMutableState", "CommitPrefEdits"
    )
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        myDatabase = Database(applicationContext)
        Data = Database(this)

        println(SimpleDateFormat("MMM").format(cal.time))

        println("Month name : $month")   // current month
        val Total_Days =
            Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)   // total Days of month
        val dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)     // day of week
        var day_pos = 0  // day name

        for (i in 0 until list.size) {

            if (list[i] == dayOfWeek.toString()) {
                day_pos = i
                break
            }
        }

        val dayofMonth = mutableStateListOf<String>()      // total day of month

        for (i in 0 until Total_Days) {

            dayofMonth.add(list[day_pos])
            day_pos++

        }

        // today date

        val formatter = SimpleDateFormat("dd")
        val date1 = Date()
        val defaultDate = formatter.format(date1).toInt()

        setContent {

            val listState = rememberLazyGridState(initialFirstVisibleItemIndex = defaultDate - 2)
            val name = sharedPreferences.getString("name_pref", "ridi")
            val email = sharedPreferences.getString("email_pref", "gmail")
            val number = sharedPreferences.getString("number_pref", "12345")

            Calendar_AppTheme {

                val all = show()
                val allDataList = mutableStateListOf<showData>()

                for (Data in all) {

                    if (Data.date.split("-")[0] == selectDate.toString()) {
                        allDataList.add(Data)
                    }
                }

                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                ModalNavigationDrawer(
                    drawerContent = {
                        ModalDrawerSheet(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(250.dp), drawerContainerColor = star.lBlue
                        ) {
                            Spacer(
                                modifier = Modifier
                                    .height(30.dp)
                            )

                            Column(
                                modifier = Modifier
                                    .height(140.dp)
                                    .fillMaxWidth()
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .height(80.dp)
                                        .width(130.dp)
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.info),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .height(60.dp)
                                            .width(70.dp)

                                    )
                                }

                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .height(40.dp)
                                        .width(130.dp)
                                ) {
                                    Text(text = "$name", fontSize = 20.sp, color = Color.Black)
                                }
                            }

                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.Top,
                                modifier = Modifier
                                    .height(2.dp)
                                    .fillMaxWidth()
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.line),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .height(20.dp)
                                        .width(200.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(15.dp))

                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.Top,
                                modifier = Modifier
                                    .height(45.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "    Task Tracker",
                                    fontSize = 20.sp,
                                    color = star.bcolor,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily(
                                        Font(R.font.star)
                                    )
                                )
                            }


                            Row(
                                modifier = Modifier
                                    .height(60.dp)
                                    .fillMaxWidth()
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center, modifier = Modifier
                                        .height(60.dp)
                                        .width(70.dp)
                                ) {

                                    Image(
                                        painter = painterResource(R.drawable.calendar1),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .height(30.dp)
                                            .width(40.dp)
                                    )
                                }
                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .height(60.dp)
                                        .width(70.dp)
                                ) {
                                    Text(
                                        text = "Calendar",
                                        fontSize = 12.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily(Font(R.font.star)),
                                        modifier = Modifier.clickable {

                                            val intent =
                                                Intent(this@MainActivity, My_Calendar::class.java)
                                            startActivity(intent)
                                            finish()

                                        }
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier
                                    .height(40.dp)
                                    .fillMaxWidth()
                            ) {

                                Box(
                                    contentAlignment = Alignment.Center, modifier = Modifier
                                        .height(60.dp)
                                        .width(70.dp)
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.plus1),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .height(30.dp)
                                            .width(40.dp)
                                    )
                                }
                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .height(60.dp)
                                        .width(80.dp)
                                ) {
                                    Text(
                                        text = "Add Event",
                                        fontSize = 12.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily(Font(R.font.star)),
                                        modifier = Modifier.clickable {

                                            val intent =
                                                Intent(this@MainActivity, My_Calendar::class.java)
                                            startActivity(intent)

                                        }
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Row(
                                modifier = Modifier
                                    .height(30.dp)
                                    .fillMaxWidth()
                            ) {

                                Icon(
                                    painter = painterResource(R.drawable.line),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .height(20.dp)
                                        .width(200.dp)
                                )

                            }

                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.Top,
                                modifier = Modifier
                                    .height(45.dp)
                                    .fillMaxWidth()
                            ) {

                                Text(
                                    text = "    User Detail",
                                    fontSize = 20.sp,
                                    color = star.bcolor,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily(
                                        Font(R.font.star)
                                    )
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .height(60.dp)
                                    .fillMaxWidth()
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center, modifier = Modifier
                                        .height(60.dp)
                                        .width(70.dp)
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.email),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .height(25.dp)
                                            .width(30.dp)
                                    )
                                }
                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .height(60.dp)
                                        .width(140.dp)
                                ) {
                                    Text(
                                        text = "$email",
                                        fontSize = 12.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily(Font(R.font.star))
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier
                                    .height(60.dp)
                                    .fillMaxWidth()
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center, modifier = Modifier
                                        .height(40.dp)
                                        .width(70.dp)
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.call),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .height(25.dp)
                                            .width(30.dp)
                                    )
                                }
                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .height(40.dp)
                                        .width(100.dp)
                                ) {
                                    Text(
                                        text = "$number",
                                        fontSize = 12.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.W100,
                                        fontFamily = FontFamily(Font(R.font.sun))
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier
                                    .height(30.dp)
                                    .fillMaxWidth()
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.line),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .height(20.dp)
                                        .width(200.dp)
                                )
                            }

                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .height(70.dp)
                                    .width(200.dp)
                            ) {
                                ElevatedButton(
                                    onClick = {

                                        editor.clear()
                                        editor.apply()

                                        Toast.makeText(
                                            this@MainActivity,
                                            "LogOut Successfull",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        val intent =
                                            Intent(this@MainActivity, LogIn_Page::class.java)
                                        startActivity(intent)
                                        finish()

                                    },
                                    modifier = Modifier
                                        .height(43.dp)
                                        .width(90.dp),
                                    shape = RoundedCornerShape(11.dp),
                                    elevation = ButtonDefaults.elevatedButtonElevation(20.dp),
                                    colors = ButtonColors(
                                        containerColor = star.cream,
                                        contentColor = Color.White,
                                        disabledContainerColor = Color.White,
                                        disabledContentColor = Color.White
                                    )
                                ) {
                                    Text(
                                        text = "Log Out",
                                        fontSize = 16.sp,
                                        color = star.bcolor,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    },
                    drawerState = drawerState
                )
                {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = " Calendar ",
                                        fontSize = 30.sp,
                                        color = Color.Black,
                                        fontFamily = FontFamily(Font(R.font.star)),
                                        fontWeight = FontWeight.W500
                                    )
                                },
                                navigationIcon = {

                                    Icon(
                                        painter = painterResource(R.drawable.three),
                                        contentDescription = null,
                                        tint = Color.Black,
                                        modifier = Modifier
                                            .height(35.dp)
                                            .padding(2.dp)
                                            .clickable {

                                                scope.launch {

                                                    drawerState.apply {

                                                        if (isClosed) {
                                                            open()
                                                        } else {
                                                            close()
                                                        }

                                                    }

                                                }

                                            }
                                    )
                                }, actions = {

                                    Icon(
                                        painter = painterResource(R.drawable.dots),
                                        contentDescription = null,
                                        tint = Color.Black,
                                        modifier = Modifier
                                            .height(42.dp)
                                            .padding(9.dp)
                                    )

                                },
                                colors = TopAppBarDefaults.topAppBarColors(star.lpink)
                            )
                        },
                        bottomBar = {
                            BottomAppBar(
                                modifier = Modifier.height(70.dp),
                                containerColor = star.lBlue
                            ) {

                                Row(
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .width(230.dp),
                                ) {
                                    Text(
                                        text = "Hello, beautiful day!",
                                        fontSize = 18.sp,
                                        color = star.bcolor,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily(
                                            Font(R.font.title)
                                        )
                                    )
                                }
                                Row(
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .width(110.dp)
                                ) {
                                    FloatingActionButton(
                                        onClick = {

                                            val intent =
                                                Intent(this@MainActivity, My_Calendar::class.java)
                                            startActivity(intent)

                                        },
                                        modifier = Modifier
                                            .height(45.dp)
                                            .width(45.dp), containerColor = star.box
                                    ) {
                                        Icon(
                                            Icons.Outlined.Add,
                                            contentDescription = null,
                                            tint = Color.Black
                                        )
                                    }
                                }

                            }
                        }

                    ) { paddingValues ->

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = star.cream)
                                .padding(paddingValues)
                        ) {

                            Row(
                                modifier = Modifier
                                    .height(4.dp)
                                    .fillMaxWidth()
                            ) {
                            }

                            Row(
                                modifier = Modifier
                                    .height(100.dp)
                                    .fillMaxWidth()
                            ) {

                                // todo (date box)

                                LazyHorizontalGrid(
                                    rows = GridCells.Fixed(1), state = listState
                                ) {
                                    items(dayofMonth.size) { day ->

                                        Surface(
                                            modifier = Modifier
                                                .height(30.dp)
                                                .padding(7.dp)
                                                .width(85.dp),
                                            color = Color.White,
                                            shape = RoundedCornerShape(10.dp),
                                            shadowElevation = 10.dp
                                        ) {

                                            Surface(
                                                modifier = Modifier
                                                    .height(20.dp)
                                                    .width(80.dp)
                                                    .clickable {

                                                        selectDate = day + 1
                                                        allDataList.clear()

                                                        for (i in 0 until all.size) {
                                                            if (all[i].date.split("-")[0] == selectDate.toString()) {

                                                                allDataList.add(all[i])
                                                            }
                                                        }
                                                        Log.d(
                                                            "====",
                                                            "onCreate: clicked dat ${day + 1} , selected date update to $selectDate"
                                                        )
                                                    }
                                                    .padding(5.dp),
                                                color = if ("${day + 1}" == "$date") {
                                                    star.light
                                                } else {

                                                    star.box
                                                },
                                                shape = RoundedCornerShape(10.dp)
                                            ) {

                                                Column(modifier = Modifier.fillMaxSize()) {

                                                    Row(
                                                        horizontalArrangement = Arrangement.Center,
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        modifier = Modifier
                                                            .height(48.dp)
                                                            .fillMaxWidth()
                                                    ) {
                                                        Text(
                                                            text = "${day + 1}",
                                                            fontSize = 40.sp,
                                                            color = if ("${day + 1}" == "$date") {
                                                                Color.Red
                                                            } else {
                                                                Color.Black
                                                            }
                                                        )
                                                    }

                                                    Row(
                                                        horizontalArrangement = Arrangement.Center,
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        modifier = Modifier
                                                            .height(25.dp)
                                                            .fillMaxWidth()
                                                    ) {
                                                        Text(
                                                            text = dayofMonth[day],
                                                            fontSize = 12.sp,
                                                            color = star.red,
                                                            fontWeight = FontWeight.Bold,
                                                            fontFamily = FontFamily(Font(R.font.font1))
                                                        )
                                                        count = day

                                                    }

                                                }

                                            }

                                        }


                                    }

                                }


                            }

                            Row(
                                modifier = Modifier
                                    .height(520.dp)
                                    .fillMaxWidth()
                            ) {

                                if (allDataList.isEmpty()) {

                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center,
                                        modifier = Modifier.fillMaxSize(),
                                    ) {


                                        Image(
                                            painter = painterResource(R.drawable.empty2),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .height(200.dp)
                                                .width(200.dp)
                                        )
                                    }


                                } else {


                                    // todo (task note)

                                    LazyVerticalGrid(columns = GridCells.Fixed(1)) {

                                        items(allDataList.size) { index ->

                                            Surface(
                                                modifier = Modifier
                                                    .height(190.dp)
                                                    .width(90.dp)
                                                    .clickable {


                                                        val intent = Intent(
                                                            this@MainActivity,
                                                            Edit_Page::class.java
                                                        )

                                                        intent.putExtra("id", all[index].id)
                                                        intent.putExtra(
                                                            "title",
                                                            all[index].date
                                                        )
                                                        intent.putExtra(
                                                            "detail",
                                                            all[index].detail
                                                        )


                                                        startActivity(intent)
                                                        finish()

                                                    }
                                                    .padding(
                                                        top = 15.dp,
                                                        bottom = 10.dp,
                                                        start = 20.dp,
                                                        end = 20.dp
                                                    ),
                                                shape = RoundedCornerShape(10.dp),
                                                color = star.lBlue, shadowElevation = 15.dp
                                            ) {

                                                Column(modifier = Modifier.fillMaxSize()) {

                                                    Row(
                                                        modifier = Modifier
                                                            .height(25.dp)
                                                            .fillMaxWidth()
                                                    ) {

                                                        Row(
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.End,
                                                            modifier = Modifier
                                                                .height(25.dp)
                                                                .width(110.dp)
                                                        ) {
                                                            Icon(
                                                                Icons.Outlined.DateRange,
                                                                contentDescription = null,
                                                                tint = Color.White,
                                                                modifier = Modifier
                                                                    .height(23.dp)

                                                            )
                                                        }

                                                        Row(
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            modifier = Modifier
                                                                .height(25.dp)
                                                                .width(100.dp)
                                                        ) {

                                                            // todo(date fix)

                                                            Text(
                                                                text = " Menage Your Day",
                                                                fontSize = 13.sp,
                                                                color = star.bcolor
                                                            )

                                                        }

                                                        Row(
                                                            horizontalArrangement = Arrangement.End,
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            modifier = Modifier
                                                                .height(25.dp)
                                                                .width(70.dp)
                                                        ) {

                                                            Image(
                                                                painter = painterResource(R.drawable.copy),
                                                                contentDescription = null,
                                                                modifier = Modifier
                                                                    .height(40.dp)
                                                                    .width(16.dp)
                                                                    .clickable {

                                                                        Share.copyTextToClipboard(
                                                                            context = this@MainActivity,
                                                                            "${all[index].title} \n ${all[index].detail}"
                                                                        )

                                                                    }
                                                            )

                                                        }

                                                        Row(
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.End,
                                                            modifier = Modifier
                                                                .height(25.dp)
                                                                .width(30.dp)
                                                        ) {

                                                            Icon(
                                                                Icons.Outlined.Share,
                                                                contentDescription = null,
                                                                modifier = Modifier
                                                                    .height(40.dp)
                                                                    .clickable {

                                                                        Share.shareTextToWhatsApp(
                                                                            context = this@MainActivity,
                                                                            " title : ${all[index].date} \n Description : ${all[index].detail} "
                                                                        )

                                                                    }
                                                                    .padding(5.dp),
                                                                tint = Color.Black
                                                            )

                                                        }

                                                    }

                                                    Box(
                                                        contentAlignment = Alignment.Center,
                                                        modifier = Modifier.fillMaxSize()
                                                    ) {

                                                        Card(
                                                            modifier = Modifier
                                                                .height(120.dp)
                                                                .shadow(elevation = 10.dp)
                                                                .width(300.dp),
                                                            colors = CardColors(
                                                                containerColor = star.cream,
                                                                contentColor = star.cream,
                                                                disabledContainerColor = star.cream,
                                                                disabledContentColor = star.cream
                                                            )
                                                        ) {

                                                            Row(
                                                                verticalAlignment = Alignment.Bottom,
                                                                modifier = Modifier
                                                                    .height(40.dp)
                                                                    .fillMaxWidth()
                                                            ) {
                                                                Text(
                                                                    text = "  Date :  ${allDataList[index].date}",
                                                                    fontSize = 15.sp,
                                                                    color = Color.Black,
                                                                    fontFamily = FontFamily(
                                                                        Font(R.font.sun)
                                                                    )
                                                                )
                                                            }

                                                            Spacer(modifier = Modifier.height(10.dp))

                                                            Row(
                                                                modifier = Modifier
                                                                    .height(100.dp)
                                                                    .fillMaxWidth()
                                                            ) {
                                                                Text(
                                                                    text = "  Event : ${allDataList[index].detail} ",
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
                        }

                    }
                }

            }


        }

    }
}


fun show(): ArrayList<showData> {

    val DateShow = ArrayList<showData>()

    val cursor: Cursor = myDatabase.readableDatabase.rawQuery("SELECT * FROM showData", null)

    cursor.use {

        if (it.moveToFirst()) {

            do {
                val id = it.getInt(0)
                val date = it.getString(1)
                val month = it.getString(2)
                val year = it.getString(3)
                val title = it.getString(4)
                val detail = it.getString(5)

                val data = showData(
                    id = id,
                    date = date,
                    month = month,
                    year = year,
                    title = title,
                    detail = detail
                )
                DateShow.add(data)

            } while (it.moveToNext())

        }
        return DateShow

    }

}