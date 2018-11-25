package com.example.ferzamora.proyectoingsoft;

/**
 * Created by DAE on 5/3/2018.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbmsSQLiteHelper extends SQLiteOpenHelper {
    String sqlCreate = "CREATE TABLE Medicamento (id INTEGER, nombre TEXT, padecimiento TEXT, dosis TEXT, doctor TEXT)";
    public DbmsSQLiteHelper(Context c, String s, CursorFactory cf, int v){
        super(c, s, cf, v);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(sqlCreate);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqld, int ov, int nv) {
        sqld.execSQL("DROP TABLE IF EXISTS Medicamento");
        sqld.execSQL(sqlCreate);
    }
}