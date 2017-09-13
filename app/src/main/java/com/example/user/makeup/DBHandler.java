package com.example.user.makeup;
//Handler of DataBase for local data
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;

public class DBHandler extends SQLiteOpenHelper
{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "productDB.db";
    public static final String TABLE_PROFILE = "profile";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_NAME = "name";


    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION); // factory is used for cursor, set to null if not needed
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE  " + TABLE_PROFILE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_AGE + " TEXT"+
                ");";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        onCreate(db);
    }
    //get function,update onCreate and copy function to create more column
    public String getName (int id)
    {
        SQLiteDatabase db;
             db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PROFILE + " WHERE "+COLUMN_ID + " = '" + id+"' ;";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        String user="";
        user += c.getString(c.getColumnIndex(COLUMN_NAME));
        db.close();
        return user;
    }
    public String getAge (int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PROFILE + " WHERE "+COLUMN_ID + " = '" + id+"' ;";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        String user="";
        user +=  Integer.toString(c.getInt(c.getColumnIndex(COLUMN_AGE)));
        db.close();
        return user;
    }
    //functiom to set new User
    public void newUser()
    {
        Log.d("sql11", "start new user");
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, "new name");
        values.put(COLUMN_AGE, "0");
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PROFILE, null, values);
        Log.d("sql11", "end new user");
        db.close();
    }
public Long countRow()
{
    SQLiteDatabase db = getReadableDatabase();
    long cnt = DatabaseUtils.queryNumEntries(db,TABLE_PROFILE);
    db.close();
return cnt;

}
    public void update(ProfileDatabase databse)
    {
        Log.d("profile", "start update");
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(COLUMN_AGE, databse.get_age());
        value.put(COLUMN_NAME,databse.get_name());
        db.update(TABLE_PROFILE,value,COLUMN_ID+ " =? ",new String[]{1+""});// 1 for which  user (locally)
        Log.d("profile", "after update");
        db.close();
    }

}
