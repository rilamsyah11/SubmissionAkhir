package com.rizki.submissionakhir.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.rizki.submissionakhir.database.DBContract.UserFavColumns.Companion.TABLE_NAME

internal class DBHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_USER_FAVORITE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    companion object{
        private const val DATABASE_NAME = "dbgithubuser"

        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_USER_FAVORITE = "CREATE TABLE ${DBContract.UserFavColumns.TABLE_NAME}" +
                "(${DBContract.UserFavColumns._ID} INTEGER PRIMARY KEY," +
                " ${DBContract.UserFavColumns.USERNAME} TEXT NOT NULL," +
                " ${DBContract.UserFavColumns.AVATAR_URL} TEXT NOT NULL)"
    }
}