package com.example.km.fry;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by xowns on 2018-02-12.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    int db_version;
    //안드로이드에서 SQLite 데이터 베이스를 쉽게 사용할 수 있도록 도와주는 클래스
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                              int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //최초에 데이터베이스가 없는 경우, 데이터베이스 생성을 위해 호출됨
        //테이블을 생성하는 코드를 작성한다.
        String sql = "CREATE TABLE user_table( _id INTEGER PRIMARY KEY AUTOINCREMENT, ID TEXT, PW TEXT)";
        //String sql2 = "CREATE TABLE CONTENT_LIST( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, content TEXT)";

        db.execSQL(sql);
        //   db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //데이터베이스의 버전이 바뀌었을 때 호출되는 콜벡 메서드
        //버전이 바뀌었을 때 기존데이터베이스를 어떻게 변경할 것인지 작성한다.
        //각 버전의 변경 내용들을 버전마다 작성해야됨
        // String sql = "drop table food_list";
        // sqLiteDatabase.execSQL(sql);
        //onCreate(sqLiteDatabase);

        sqLiteDatabase.execSQL("INSERT INTO FOOD_List VALUES (null, '감자탕', 5000)");
    }

    public void insert(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    //유저 확인
    public boolean user_check(String id, String pw) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from user_table", null);
        while(cursor.moveToNext()) {

            Log.d("cursor1",cursor.getString(1));
            Log.d("cursor2",cursor.getString(2));

            if(cursor.getString(1).equals(id) && cursor.getString(2).equals(pw)) {
                return true;
            }
        }

        return false;
    }

    //유저 출력
    public String print_user( ) {

        SQLiteDatabase db = getReadableDatabase();
        String str = "";

        Cursor cursor = db.rawQuery("select * from user_table", null);
        while(cursor.moveToNext()) {
            str += cursor.getInt(0)
                    + " : ID =  "
                    + cursor.getString(1)
                    + ", PW = "
                    + cursor.getString(2)
                    + "\n";
        }

        return str;
    }
}
