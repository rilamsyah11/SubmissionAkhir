package com.rizki.submissionakhir.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.rizki.submissionakhir.database.DBContract.UserFavColumns.Companion.TABLE_NAME
import com.rizki.submissionakhir.database.DBContract.UserFavColumns.Companion._ID
import kotlin.jvm.Throws

class UserFavHelper (context: Context) {
    private var dBHelper: DBHelper = DBHelper(context)
    private lateinit var databases: SQLiteDatabase

    @Throws(SQLException::class)
    fun open(){
        databases = dBHelper.writableDatabase
    }

    fun close(){
        dBHelper.close()

        if (databases.isOpen)
            databases.close()
    }

    fun queryAll(): Cursor {
        return databases.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC"
        )
    }

    fun queryById(id: String): Cursor {
        return databases.query(
            DATABASE_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null
        )
    }

    fun tambah(values: ContentValues?): Long{
        return databases.insert(DATABASE_TABLE,null, values)
    }

    fun hapusById(id : Int): Int{
        return databases.delete(DATABASE_TABLE, "$_ID = $id", null)
    }

    companion object{
        private const val DATABASE_TABLE = TABLE_NAME

        private var INSTANCE: UserFavHelper? = null
        fun getInstance(context: Context) : UserFavHelper =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: UserFavHelper(context)
            }
    }


}