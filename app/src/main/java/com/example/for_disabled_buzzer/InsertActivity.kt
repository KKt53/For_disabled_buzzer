package com.example.for_disabled_buzzer

import android.content.ContentValues
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class InsertActivity : AppCompatActivity()  {


    var name = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)

        //setSupportActionBar(findViewById(R.id.toolbar_insert))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val edit_Name = findViewById<EditText>(R.id.Name_view)
        val edit_Handle = findViewById<EditText>(R.id.Handle_view)

        val buttonSave = findViewById<Button>(R.id.button_Handle)

        val intent = intent
        val name = intent.getStringExtra("Name")
        val id = intent.getIntExtra("id", 0)
        val handle = intent.getStringExtra("Handle")
        val upd_flg = intent.getIntExtra("upd_flag", 0)

        // lambda
        buttonSave.setOnClickListener { v: View? ->
            val helper = DatabaseHelper(this)

            // エディットテキストのテキストを取得
            val text_name = edit_Name.getText().toString()
            val text_handle = edit_Handle.getText().toString()


            helper.readableDatabase.use { db ->
                // 入力されたタイトルとコンテンツをContentValuesに設定
                // ContentValuesは、項目名と値をセットで保存できるオブジェクト
                val cv = ContentValues()
                cv.put(DB_class.DBEntry.Name, text_name)
                cv.put(DB_class.DBEntry.Handle, text_handle)

                if(upd_flg == 1) {
                    db.update(DB_class.DBEntry.Table_Name, cv, "_id = ?", arrayOf<String>(id.toString()))
                }
                else{
                    // データ新規登録
                    db.insert(DB_class.DBEntry.Table_Name, null, cv)
                }

                finish()
            }
        }

        if(upd_flg == 1){
            edit_Name.setText(name)
            edit_Handle.setText(handle)
        }

        val actionBar = supportActionBar
        if (actionBar != null) {
            if(upd_flg == 1){
                actionBar.title = "下記の対処法をしてください"
            }
            else{
                actionBar.title = "対処法設定画面"
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

        // データを一覧表示
        onShow()
    }

    protected  fun onShow(){

    }
}