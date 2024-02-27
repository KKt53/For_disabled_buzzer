package com.example.for_disabled_buzzer

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListActivity : AppCompatActivity() {

    private var helper: DatabaseHelper? = null
    var sc_adapter: MainListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        //setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fab = findViewById<FloatingActionButton?>(R.id.fab)

        fab.setOnClickListener(View.OnClickListener {
            val intent = Intent(application, InsertActivity::class.java)
            startActivity(intent)
        })

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "困っていること一覧"
        }
    }


    override fun onStart() {
        super.onStart()

        onShow()
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

    // 削除ボタン　タップ時に呼び出されるメソッド
    fun btnDel_onClick(view: View) {

        // MainListAdapterで設定されたリスト内の位置を取得
        val pos = view.tag as Int

        // アダプターから、_idの値を取得
        val id = (sc_adapter?.getItem(pos) as Cursor).getInt(0)
        helper?.writableDatabase.use { db ->
            db?.delete(
                DB_class.DBEntry.Table_Name,
                DB_class.DBEntry._ID + " = ?",
                arrayOf<String>(id.toString())
            )
        }

        // データを一覧表示
        onShow()
    }

    private fun onShow() {
        // データベースヘルパーを準備
        helper = DatabaseHelper(this)

        // 読み込みモードでデータベースをオープン
        helper?.readableDatabase.use { db ->

            // データベースを検索
            val cursor = db?.query(
                DB_class.DBEntry.Table_Name, null, null,
                null, null, null, null, null
            )

            //db.query(テーブル名, String[](カラム名の配列),条件(whereの内容),
            // 検索条件のパラメータに置換する値の配列,groupBy句,having句,orderBy句)

            // 検索結果から取得する項目を定義
            val from = arrayOf<String?>(DB_class.DBEntry.Name)

            // データを設定するレイアウトのフィールドを定義
            val to = intArrayOf(R.id.title)

            // ListViewの1行分のレイアウト(row_main.xml)と検索結果を関連付け
            sc_adapter = MainListAdapter(
                this, R.layout.raw_main, cursor, from, to, 0
            )

            // activity_main.xmlに定義したListViewオブジェクトを取得
            val list = findViewById<ListView>(R.id.mainList)

            // ListViewにアダプターを設定
            list.setAdapter(sc_adapter)



            // リストの項目をクリックしたときの処理
            list.onItemClickListener =
                AdapterView.OnItemClickListener { av, view, position, id -> //　クリックされた行のデータを取得
                    val cursor = av.getItemAtPosition(position) as Cursor

                    // テキスト登録画面 Activity へのインテントを作成
                    val intent: Intent = Intent(
                        this@ListActivity,
                        InsertActivity::class.java
                    )
                    //intent.putExtra("id", id)
                    intent.putExtra("id", cursor.getInt(0))
                    intent.putExtra(DB_class.DBEntry.Name, cursor.getString(1))
                    intent.putExtra(DB_class.DBEntry.Handle, cursor.getString(2))
                    intent.putExtra("upd_flag",1)

                    // アクティビティを起動
                    startActivity(intent)
                }
        }
    }



}