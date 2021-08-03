package com.example.yolharakatiqoidalari.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.yolharakatiqoidalari.models.Sign

class MyDbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1), MyDbService {
    companion object {
        const val DB_NAME = "sign_db"
        const val SIGNS_TABLE_NAME = "signs_table"
        const val ID = "id"
        const val SIGN_NAME = "table_name"
        const val SIGN_DESCRIPTION1 = "description1"
        const val SIGN_TYPE = "type"
        const val SIGN_IMAGE = "image"
        const val SIGN_LIKABLE = "likable"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query =
            "create table $SIGNS_TABLE_NAME ($ID integer not null primary key autoincrement , $SIGN_NAME  text not null, $SIGN_DESCRIPTION1 text not null,$SIGN_TYPE text not null,$SIGN_IMAGE blob not null,$SIGN_LIKABLE integer not null)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun addSign(sign: Sign) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(SIGN_NAME, sign.sign_name)
        contentValues.put(SIGN_DESCRIPTION1, sign.sign_desc)
        contentValues.put(SIGN_TYPE, sign.sign_type)
        contentValues.put(SIGN_IMAGE, sign.sign_image)
        contentValues.put(SIGN_LIKABLE, sign.sign_likable)
        database.insert(SIGNS_TABLE_NAME, null, contentValues)
        database.close()
    }

    override fun editSign(sign: Sign) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, sign.sign_id)
        contentValues.put(SIGN_NAME, sign.sign_name)
        contentValues.put(SIGN_DESCRIPTION1, sign.sign_desc)
        contentValues.put(SIGN_TYPE, sign.sign_type)
        contentValues.put(SIGN_IMAGE, sign.sign_image)
        contentValues.put(SIGN_LIKABLE, sign.sign_likable)
        database.update(
            SIGNS_TABLE_NAME,
            contentValues,
            "$ID=?",
            arrayOf(sign.sign_id.toString())
        )
    }

    override fun deleteSign(sign: Sign) {
        val database = writableDatabase
        database.delete(SIGNS_TABLE_NAME, "$ID=? ", arrayOf(sign.sign_id.toString()))
        database.close()
    }

    override fun getAll(): ArrayList<Sign> {
        val query = "select *from $SIGNS_TABLE_NAME"
        val list = ArrayList<Sign>()
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val desc = cursor.getString(2)
                val type = cursor.getString(3)
                val image = cursor.getBlob(4)
                val likable = cursor.getInt(5)
                list.add(Sign(id, name, desc, type, image, likable))
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun getbyId(id: Int): Sign {
        val database = this.readableDatabase
        val cursor = database.query(
            SIGNS_TABLE_NAME,
            arrayOf(ID, SIGN_NAME, SIGN_DESCRIPTION1, SIGN_TYPE, SIGN_IMAGE, SIGN_LIKABLE),
            "$ID=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        cursor.moveToFirst()
        return Sign(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getBlob(4),
            cursor.getInt(5)
        )

    }

    override fun getLikedSigns(): ArrayList<Sign> {
        val query = "select *from $SIGNS_TABLE_NAME where $SIGN_LIKABLE =1"
        val list = ArrayList<Sign>()
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val desc = cursor.getString(2)
                val type = cursor.getString(3)
                val image = cursor.getBlob(4)
                val likable = cursor.getInt(5)
                list.add(Sign(id, name, desc, type, image, likable))
            } while (cursor.moveToNext())
        }
        return list
    }
}