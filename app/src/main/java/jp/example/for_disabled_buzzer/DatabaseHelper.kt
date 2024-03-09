package jp.example.for_disabled_buzzer

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context?): SQLiteOpenHelper(context, "DB.sqlite", null, 3) {


    override fun onCreate(db: SQLiteDatabase?) {
        db?.let {
            it.execSQL(
                "CREATE TABLE " + DB_class.DBEntry.Table_Name +
                        "(" +
                        DB_class.DBEntry._ID + " INTEGER PRIMARY KEY," +
                        DB_class.DBEntry.Name + " TEXT DEFAULT '困っていることの細かい問題を入力してください'," +
                        DB_class.DBEntry.Handle + " TEXT DEFAULT '対処法を書いてください'" +
                        ")"
            )
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {
//        p0.execSQL("DROP TABLE IF EXISTS " + DB_class.DBEntry.Table_Name)
//        onCreate(p0)
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
    }
}