package com.example.contentproviderwithmvvm

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import com.example.contentproviderwithmvvm.database.MyHelper

class MyContentProvider : ContentProvider() {
    companion object {
        private const val PROVIDER_NAME = "com.example.contentproviderwithmvvm/MyContentProvider"
        val URL = "content://$PROVIDER_NAME/MY_TABLE"
        val CONTENT_URI = Uri.parse(URL)

        val ID = "_id"
        val NAME = "NAME"
        val LAST_NAME = "LAST_NAME"
    }

    private var db: SQLiteDatabase? = null
    override fun onCreate(): Boolean {
        val helper = MyHelper(context!!)
        db = helper.writableDatabase
        return db != null
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return db?.query("MY_TABLE", projection, selection, selectionArgs, null, null, sortOrder)
    }

    override fun getType(p0: Uri): String? {
        return "vnd.android.cursor.dir/vnd.example.my_table"
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        db?.insert("MY_TABLE", null, values)
        context?.contentResolver?.notifyChange(uri, null)
        return uri
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val counter = db?.delete("MY_TABLE", selection, selectionArgs)
        context?.contentResolver?.notifyChange(uri, null)
        return counter!!
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        val int = db?.update("MY_TABLE", values, selection, selectionArgs)
        context?.contentResolver?.notifyChange(uri,null)
        return int!!
    }
}
