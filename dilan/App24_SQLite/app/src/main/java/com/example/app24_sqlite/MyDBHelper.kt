package com.example.app24_sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Color
import android.view.Gravity
import android.widget.TableRow
import android.widget.TextView

class MyDBHelper(private val context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        val DB_NAME = "mydb.db"
        val DB_VERSION = 1
        val TABLE_NAME = "products"
        val PID = "pid"
        val PNAME = "pname"
        val PQUANTITY = "pquantity"
    }

    fun getAllRecord() {
        val strSQL = "select * from $TABLE_NAME;"
        val db = readableDatabase
        val cursor = db.rawQuery(strSQL, null)
        if (cursor.count != 0) showRecord(cursor)
        cursor.close()
        db.close()
    }

    private fun showRecord(cursor: Cursor) {
        cursor.moveToFirst()
        val attrCount = cursor.columnCount
        val activity = context as MainActivity
        activity.binding.tableLayout.removeAllViewsInLayout()

        // 타이틀 만들기
        val tableRow = TableRow(activity)
        val rowParam = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )
        tableRow.layoutParams = rowParam
        val viewParam = TableRow.LayoutParams(0, 100, 1F)
        for (i in 0 until attrCount) {
            val textView = TextView(activity)
            textView.layoutParams = viewParam
            textView.text = cursor.getColumnName(i)
            textView.setBackgroundColor(Color.LTGRAY)
            textView.textSize = 15.0F
            textView.gravity = Gravity.CENTER
            tableRow.addView(textView)
        }
        activity.binding.tableLayout.addView(tableRow)
        if (cursor.count == 0) return

        //레코드 추가하기
        do {
            val row = TableRow(activity)
            row.layoutParams = rowParam
            for (i in 0 until attrCount) {
                val textView = TextView(activity)
                textView.layoutParams = viewParam
                textView.text = cursor.getString(i)
                textView.textSize = 13.0F
                textView.gravity = Gravity.CENTER
                row.addView(textView)
            }
            activity.binding.tableLayout.addView(row)
        } while (cursor.moveToNext())
    }

    fun insertProduct(product: Product): Boolean {
        val values = ContentValues()
        values.put(PNAME, product.pName)
        values.put(PQUANTITY, product.pQuantity)
        val db = writableDatabase
        val flag = db.insert(TABLE_NAME, null, values) > 0
        db.close()
        return flag
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "create table if not exists $TABLE_NAME(" +
                "$PID integer primary key autoincrement, " +
                "$PNAME text, " +
                "$PQUANTITY integer);"

        db!!.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTable = "drop table if exists $TABLE_NAME"
        db!!.execSQL(dropTable)
        onCreate(db)
    }

    fun findProduct(name: String): Boolean {
        val strSQL = "select * from $TABLE_NAME where $PNAME = '$name';"
        val db = readableDatabase
        val cursor = db.rawQuery(strSQL, null)
        val flag = cursor.count != 0
        showRecord(cursor)
        cursor.close()
        db.close()

        return flag
    }
}






















