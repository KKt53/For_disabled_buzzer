package com.example.for_disabled_buzzer

import android.provider.BaseColumns

class DB_class private constructor() {
    object DBEntry : BaseColumns {
        const val Table_Name = "DB"
        const val _ID = "_id"
        const val Name = "Name"
        const val Handle = "Handle"
    }
}