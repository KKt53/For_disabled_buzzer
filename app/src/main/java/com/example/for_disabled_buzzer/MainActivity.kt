package com.example.for_disabled_buzzer

import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private var sound = 0
    private var change_swich = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        //setSupportActionBar(findViewById(R.id.main_toolbar))

        val fab = findViewById<FloatingActionButton?>(R.id.fab)

        val btn = findViewById<Button>(R.id.button_buzzer)

        fab.setOnClickListener {
            val intent = Intent(application, ListActivity::class.java)
            startActivity(intent)
        }

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ALARM)
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
            .build()

        val soundPool = SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            .setMaxStreams(1)
            .build()

        sound = soundPool.load(this, R.raw.nc317430, 1)

        soundPool.setOnLoadCompleteListener{soundPool,sampleId,status ->
            Log.d("debug", "sampleID=$sampleId")
            Log.d("debug", "status=$status")
        }

        btn.setOnClickListener{
            // 再生
            // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
            if (change_swich == false){
                soundPool.play(sound, 1.0f, 1.0f, 0, 0, 1.0f)
                change_swich = true
            }
            else{
                soundPool.stop(soundPool.play(sound, 1.0f, 1.0f, 0, 0, 1.0f))
                change_swich = false
            }

        }

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "ブザーを押すと音が鳴ります"
        }
    }

    override fun onStart() {
        super.onStart()

        val sharedPref = getSharedPreferences("障害の名前", Context.MODE_PRIVATE)

        val savedText = sharedPref.getString("key", "障害名を入力してください")

        val editText = findViewById<TextView>(R.id.Name_text)

        editText.setText(savedText)
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if(id == R.id.settings_disabled){
            val intent = Intent(application, SettingActivity::class.java)
            startActivity(intent)
        }
        return true
    }


}