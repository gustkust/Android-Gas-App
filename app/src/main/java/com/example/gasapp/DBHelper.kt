package com.example.gasapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlin.math.roundToInt


class DBHelper(context: Context?) : SQLiteOpenHelper(
    context, DATABASE_NAME,
    null, DATABASE_VER
) {
    companion object {
        private val DATABASE_VER = 2
        private val DATABASE_NAME = "EDMTDB.db"

        private val USER_TABLE_NAME = "User"
        private val COL_ID = "Id"
        private val COL_NAME = "Name"
        private val COL_PASSWORD = "Password"
        private val COL_PICTURE = "Picture"

        private val ENTRY_TABLE_NAME = "Entry"
        private val COL_USER_ID = "User_Id"
        private val COL_TYPE = "Type"
        private val COL_PRICE = "Price"
        private val COL_AMOUNT = "Amount"
        private val COL_DATE = "Date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_USER_TABLE_QUERY =
            ("CREATE TABLE $USER_TABLE_NAME (" +
                    "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COL_NAME TEXT, " +
                    "$COL_PASSWORD TEXT, " +
                    "$COL_PICTURE TEXT" +
                    ")")

        val CREATE_ENTRY_TABLE_QUERY =
            ("CREATE TABLE $ENTRY_TABLE_NAME (" +
                    "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COL_USER_ID INTEGER, " +
                    "$COL_TYPE TEXT, " +
                    "$COL_PRICE DOUBLE(8, 2), " +
                    "$COL_AMOUNT TEXT, " +
                    "$COL_DATE DATE DEFAULT (datetime('now','localtime'))" +
                    ")")

        db!!.execSQL(CREATE_USER_TABLE_QUERY)
        db.execSQL(CREATE_ENTRY_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $USER_TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $ENTRY_TABLE_NAME")
        onCreate(db)
    }

    // false if name taken
    fun addUser(user: User): Boolean {
        val selectQuery = "SELECT $COL_NAME FROM $USER_TABLE_NAME"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                if (user.name == cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME))) {
                    return false
                }
            } while (cursor.moveToNext())
        }

        val values = ContentValues()
        values.put(COL_NAME, user.name)
        values.put(COL_PASSWORD, user.password)
        values.put(COL_PICTURE, user.picture)

        db.insert(USER_TABLE_NAME, null, values)
        db.close()

        return true
    }


    fun addEntry(entry: Entry) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COL_USER_ID, entry.user_id)
        values.put(COL_TYPE, entry.type)
        values.put(COL_PRICE, entry.price)
        values.put(COL_AMOUNT, entry.amount)

        db.insert(ENTRY_TABLE_NAME, null, values)
        db.close()
    }


    fun checkPassword(login: String, password: String): Boolean {
        val selectQuery =
            "SELECT * FROM $USER_TABLE_NAME WHERE $COL_NAME = '$login' and $COL_PASSWORD = '$password'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
//            return User(
//                cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
//                cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
//                cursor.getString(cursor.getColumnIndexOrThrow(COL_PASSWORD)),
//                cursor.getString(cursor.getColumnIndexOrThrow(COL_PICTURE))
//            )
            return true
        }
        return false
    }


    fun getUsersEntries(user: User): ArrayList<Entry> {
        val entries = ArrayList<Entry>()
        val selectQuery = "SELECT * FROM $ENTRY_TABLE_NAME WHERE $COL_USER_ID = $user.id"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                entries.add(
                    Entry(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_USER_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_TYPE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COL_PRICE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COL_AMOUNT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE))
                    )
                )
            } while (cursor.moveToNext())
        }
        db.close()
        return entries
    }


    fun getAverageFuelPrice(user_id: Int): Double {
        val selectQuery =
            "SELECT $COL_PRICE, $COL_AMOUNT, $COL_TYPE FROM $ENTRY_TABLE_NAME WHERE $COL_USER_ID = $user_id"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        var price_sum = 0.0
        var amount_sum = 0.0
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(cursor.getColumnIndexOrThrow(COL_TYPE)) == "gas") {
                    price_sum += cursor.getDouble(cursor.getColumnIndexOrThrow(COL_PRICE))
                    amount_sum += cursor.getDouble(cursor.getColumnIndexOrThrow(COL_AMOUNT))
                }
            } while (cursor.moveToNext())
        }
        if (price_sum == 0.0 || amount_sum == 0.0) {
            return 0.0
        }
        return (price_sum / amount_sum * 100.0).roundToInt() / 100.0
    }


    fun getUsers(): ArrayList<UserBasicInfo> {
        val selectQuery = "SELECT * FROM $USER_TABLE_NAME"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        val users = ArrayList<UserBasicInfo>()
        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME))
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)).toString()
                var date = "No entries"

                val getDateQuery = "SELECT $COL_DATE FROM $ENTRY_TABLE_NAME WHERE $COL_USER_ID = $id ORDER BY $COL_DATE DESC LIMIT 1"
                val cursor_date = db.rawQuery(getDateQuery, null)
                if (cursor_date.moveToFirst()) {
                    date = cursor_date.getString(cursor_date.getColumnIndexOrThrow(COL_DATE)).substring(0, 10)
                }

                users.add(
                    UserBasicInfo(
                        name,
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_PICTURE)),
                        getAverageFuelPrice(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID))),
                        date
                    )
                )
            } while (cursor.moveToNext())
        }
        db.close()
        return users
    }


    fun getUserByName(name: String): User? {
        val selectQuery = "SELECT * FROM $USER_TABLE_NAME WHERE $COL_NAME = \"$name\""
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            return User(
                cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_PASSWORD)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_PICTURE))
            )
        }
        return null
    }


    fun createTables() {
        val db = this.writableDatabase

        val CREATE_USER_TABLE_QUERY =
            ("CREATE TABLE $USER_TABLE_NAME (" +
                    "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COL_NAME TEXT, " +
                    "$COL_PASSWORD TEXT, " +
                    "$COL_PICTURE TEXT" +
                    ")")

        val CREATE_ENTRY_TABLE_QUERY =
            ("CREATE TABLE $ENTRY_TABLE_NAME (" +
                    "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COL_USER_ID INTEGER, " +
                    "$COL_TYPE TEXT, " +
                    "$COL_PRICE DOUBLE(8, 2), " +
                    "$COL_AMOUNT TEXT, " +
                    "$COL_DATE DATE DEFAULT (datetime('now','localtime'))" +
                    ")")

        db!!.execSQL(CREATE_USER_TABLE_QUERY)
        db.execSQL(CREATE_ENTRY_TABLE_QUERY)
    }


    fun deleteDataBase() {
        val db = this.writableDatabase
        db.execSQL("DROP TABLE $USER_TABLE_NAME")
        db.execSQL("DROP TABLE $ENTRY_TABLE_NAME")
        db.close()
    }
}