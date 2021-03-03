package com.autumnsun.foreksinternship.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "favorite.db"
        val TABLE_NAME = "table_favorite"
        val KEY_ID = "_id"
        val KEY_NAME = "name"
        // val KEY_FAVORITE = "date"

        private var mInstance: DBHelper? = null


        @Synchronized fun getInstance(context: Context): DBHelper {
            if (mInstance == null) {
                mInstance = DBHelper(context.applicationContext)
            }
            return mInstance as DBHelper
        }
    }

    //create table
    override fun onCreate(db: SQLiteDatabase?) = createTable(db)


    //upgrade Table
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }

    private fun createTable(db: SQLiteDatabase?) {
        val CREATE_TASK_TABLE =
            "CREATE TABLE $TABLE_NAME($KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT, $KEY_NAME TEXT)"
        db?.execSQL(CREATE_TASK_TABLE)
    }
}