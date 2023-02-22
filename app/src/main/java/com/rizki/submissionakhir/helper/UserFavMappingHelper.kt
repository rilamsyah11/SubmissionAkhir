package com.rizki.submissionakhir.helper

import android.database.Cursor
import com.rizki.submissionakhir.database.DBContract
import com.rizki.submissionakhir.response.GithubUser

object UserFavMappingHelper {
    fun mapCursorToArrayListUser(usersCursor: Cursor?): ArrayList<GithubUser>{
        val userFavorite = ArrayList<GithubUser>()

        usersCursor?.apply {
            while (moveToNext()){
                val id = getInt(getColumnIndexOrThrow(DBContract.UserFavColumns._ID))
                val username = getString(getColumnIndexOrThrow(DBContract.UserFavColumns.USERNAME))
                val avatar = getString(getColumnIndexOrThrow(DBContract.UserFavColumns.AVATAR_URL))

                userFavorite.add(GithubUser(id, username,null,null,null,avatar,null))
            }
        }
        return userFavorite
    }
}