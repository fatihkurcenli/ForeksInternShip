package com.autumnsun.foreksinternship.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.autumnsun.foreksinternship.fragments.FavoriteFragment

class FavoriteRepository(var context: Context) {
    private var mDBHelper: DBHelper = DBHelper.getInstance(context)

    fun getAllFavorite(): ArrayList<FavoriteModel> {
        var list = ArrayList<FavoriteModel>()
        val db = mDBHelper.readableDatabase
        val query = "SELECT ${DBHelper.KEY_ID}, ${DBHelper.KEY_NAME} FROM ${DBHelper.TABLE_NAME}"
        val cursor: Cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID))
                val name = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME))
                val favorite = FavoriteModel(id, name)
                list.add(favorite)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return list
    }

    fun insterFavorite(favoriteModel: FavoriteModel): Int {
        val db = mDBHelper.writableDatabase
        val values = ContentValues()
        values.apply {
            put(DBHelper.KEY_NAME, favoriteModel.favorite)
        }
        var id = db.insert(DBHelper.TABLE_NAME, null, values)
        db.close()
        return id.toInt()
    }

}