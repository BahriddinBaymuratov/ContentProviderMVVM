package com.example.contentproviderwithmvvm.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyHelper(context: Context) : SQLiteOpenHelper(
    context, "MySQL", null,1){

    override fun onCreate(db: SQLiteDatabase?) {
        val query =
            "CREATE TABLE MY_TABLE (_id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, NAME TEXT NOT NULL, LAST_NAME TEXT NOT NULL)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS MY_TABLE")
        onCreate(db)
    }
}