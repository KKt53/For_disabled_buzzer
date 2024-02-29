package jp.example.for_disabled_buzzer

import android.content.Context
import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SimpleCursorAdapter

class MainListAdapter  // コンストラクタ
    (context: Context?, layout: Int, c: Cursor?, from: Array<String?>?, to: IntArray?, flags: Int) :
    SimpleCursorAdapter(context, layout, c, from, to, flags) {

    private var helper: DatabaseHelper? = null

    private var d_context: Context? = context

    // 指定データのビューを取得
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)

        // 削除ボタン オブジェクトを取得
        val btnDel = view.findViewById<View>(R.id.button_delete) as ImageButton

        // ボタンにリスト内の位置を設定
        btnDel.tag = position

        helper = DatabaseHelper(d_context)

        val id = (getItem(position) as Cursor).getInt(0)

        val pos = arrayOf<String>(id.toString())

//        btnDel.setOnClickListener{
//            helper?.writableDatabase.use { db ->
//                db?.delete(
//                    DB_class.DBEntry.Table_Name,
//                    DB_class.DBEntry._ID + " = ?",
//                    pos)
//            }
//        }

        //ListActivity().onShow()

        return view
    }
}
