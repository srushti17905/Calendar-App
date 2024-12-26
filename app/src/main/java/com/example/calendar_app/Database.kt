package com.example.calendar_app

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class Database(context: Context) : SQLiteOpenHelper(context, "mydb.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {

        val show =
            "CREATE TABLE showData (ID INTEGER Primary key autoincrement , date text , month text , year text , title text , detail text)"
        db?.execSQL(show)

        val table =
            "CREATE TABLE login (ID INTEGER Primary key autoincrement , name text , email text , number text , pass text)"
        db?.execSQL(table)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }


    fun insertShow(date: String, month: String, year: String, title: String, detail: String) {

        val insert =
            "INSERT INTO showData (date , month , year , title , detail ) VALUES ('$date' , '$month' , '$year' , '$title' , '$detail')"

        try {
            writableDatabase.execSQL(insert)
        } catch (e: Exception) {

            Log.d("==>", "insertShow: ${e.localizedMessage}")

        }

    }

    fun insertData(name: String, email: String, number: String, pass: String) {

        val insert =
            "INSERT INTO login (name , email , number , pass) VALUES ('$name' , '$email' , '$number' , '$pass')"

        try {
            writableDatabase.execSQL(insert)
        } catch (e: Exception) {

            Log.d("===>", "insertData: ${e.localizedMessage}")

        }

    }

    fun SelectData(name : String , pass : String) : Cursor{

        val select = "SELECT * FROM login WHERE name = '$name' AND pass = '$pass'"
        val cursor = readableDatabase.rawQuery(select , null)
        return cursor

    }

    fun updateData(id: Int, newTitle: String, newDetail: String) {

        val update =
            "UPDATE showData SET title = '$newTitle' , detail = '$newDetail' WHERE id = '$id'"
        writableDatabase.execSQL(update)

    }

    fun deleteData(id: Int) {

        val delete = "DELETE FROM showData WHERE id = '$id'"
        writableDatabase.execSQL(delete)

    }
}