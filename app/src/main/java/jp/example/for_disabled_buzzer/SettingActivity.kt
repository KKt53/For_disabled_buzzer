package jp.example.for_disabled_buzzer

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
class SettingActivity : AppCompatActivity() {

    lateinit var mAdView : AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        //setSupportActionBar(findViewById(R.id.toolbar_setting))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "困っていることを書いてください"
        }

        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    override fun onStart() {
        super.onStart()

        //val sharedPref = getSharedPreferences("困っていること", Context.MODE_PRIVATE)

        //val savedText = sharedPref.getString("key", "困っていることを入力してください")
        val sharedPref = getSharedPreferences("困っている事の名前", Context.MODE_PRIVATE)

        val savedText = sharedPref.getString("key", "困っていることを入力してください")

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