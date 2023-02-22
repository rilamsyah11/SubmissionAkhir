package com.rizki.submissionakhir.database

import android.provider.BaseColumns

internal class DBContract {
    internal class UserFavColumns: BaseColumns {
        companion object{
            const val TABLE_NAME = "user_favorite"
            const val _ID = "_id"
            const val USERNAME = "username"
            const val AVATAR_URL = "avatar_url"
        }
    }
}