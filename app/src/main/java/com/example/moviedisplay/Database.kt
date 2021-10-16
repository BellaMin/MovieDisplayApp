package com.example.moviedisplay

import android.content.ClipDescription
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.Serializable

/**
 * Let's start by creating our database CRUD helper class
 * based on the SQLiteHelper.
 */
class Database(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    /**
     * Our onCreate() method.
     * Called when the database is created for the first time. This is
     * where the creation of tables and the initial population of the tables
     * should happen.
     */
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $F_M_Table_Name ( _id INTEGER PRIMARY KEY " +
                ", _title TEXT )")

        db.execSQL("CREATE TABLE $M_TABLE_NAME ( $ID INTEGER PRIMARY KEY " +
                ", $TITLE TEXT , $DESCRIPTION TEXT, $POSTER TEXT, $BACK_DROP TEXT, $RATING INTEGER ," +
                " $RELEASED_DATE INTEGER , $IS_FAVORITE TEXT )")
    }

    /**
     * Let's create Our onUpgrade method
     * Called when the database needs to be upgraded. The implementation should
     * use this method to drop tables, add tables, or do anything else it needs
     * to upgrade to the new schema version.
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + F_M_Table_Name)
        db.execSQL("DROP TABLE IF EXISTS " + M_TABLE_NAME)
        onCreate(db)
    }

    /**
     * Let's create our insertData() method.
     * It Will insert data to SQLIte database.
     */
    fun insertFavorite(title: String, id: Long) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(TITLE, title)
        contentValues.put(ID, id)
        db.insert(F_M_Table_Name, null, contentValues)
        db.close()
    }

    fun insertAllMovie(title:String, id:Long, description: String, poster : String, backDrop : String, rating : Float, releasedDate : String){
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(TITLE, title)
        contentValues.put(ID, id)
        contentValues.put(DESCRIPTION, description)
        contentValues.put(POSTER, poster)
        contentValues.put(BACK_DROP,backDrop)
        contentValues.put(RATING,rating)
        contentValues.put(RELEASED_DATE,releasedDate)
        contentValues.put(IS_FAVORITE,"No")
        db.insert(M_TABLE_NAME, null, contentValues)
        db.close()
    }



    /**
     * Let's create  a method to update a row with new field values.
     */

    fun isFavorite(id:Long) : Cursor{
        val db = this.writableDatabase
        val res = db.rawQuery("SELECT $IS_FAVORITE FROM $M_TABLE_NAME WHERE $ID = $id", null)
        return res;
    }

     fun updateData(id: Long,isFavorite :String):
            Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(IS_FAVORITE, isFavorite)
        db.update(M_TABLE_NAME, contentValues, "ID = ?", arrayOf(id.toString()))
        return true
    }



    /**
     * Let's create a function to delete a given row based on the id.
     */
    fun deleteData(id : Long) : Int {
        val db = this.writableDatabase
        return db.delete(M_TABLE_NAME,"ID = ?", arrayOf(id.toString()))
    }

    /**
     * The below getter property will return a Cursor containing our dataset.
     */
    val allData : Cursor
        get() {
            val db = this.writableDatabase
            val res = db.rawQuery("SELECT * FROM " + M_TABLE_NAME, null)
            return res
        }

    /**
     * Let's create a companion object to hold our static fields.
     * A Companion object is an object that is common to all instances of a given
     * class.
     */
    companion object {
        val DATABASE_NAME = "local.db"
        val F_M_Table_Name = "_favorite_movies"
        val ID = "_id"
        val M_TABLE_NAME = "_local_movies"
        val TITLE = "_title"
        val DESCRIPTION = "_description"
        val POSTER = "_poster"
        val BACK_DROP = "_back_drop"
        val RATING = "_rating"
        val RELEASED_DATE = "_release_date"
        val IS_FAVORITE = "_is_favorite"
    }
}