package com.example.for_disabled_buzzer

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        //setSupportActionBar(findViewById(R.id.toolbar_setting))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "障害の名前を書いてください"
        }
    }

    override fun onStart() {
        super.onStart()

        val sharedPref = getSharedPreferences("障害の名前", Context.MODE_PRIVATE)

        val savedText = sharedPref.getString("key", "障害名を入力してください")

        val editText = findViewById<EditText>(R.id.edit_setting)

        editText.setText(savedText)

        val button = findViewById<Button>(R.id.setting_button)

        button.setOnClickListener{
            val input_text = editText.text.toString()

            sharedPref.edit().putString("key", input_text).apply()
            finish()
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
}