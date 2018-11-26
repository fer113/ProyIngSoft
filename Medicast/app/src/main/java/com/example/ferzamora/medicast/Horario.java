package com.example.ferzamora.medicast;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Horario extends AppCompatActivity {
    Button jbnL;
    TextView jtvL;
    SQLiteDatabase sqld;
    String id_s;
    int id;
    String nombre ;
    String padeci;
    String dosis;
    String doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*id = (int) getIntent().getExtras().getInt("id");
        nombre = getIntent().getExtras().getString("nombre");
        padeci = getIntent().getExtras().getString("padecimiento");
        dosis = getIntent().getExtras().getString("dosis");
        doctor = getIntent().getExtras().getString("doctor");*/
        setContentView(R.layout.activity_horario);
        jbnL = (Button) findViewById(R.id.BtnSave);
        jtvL = (TextView) findViewById(R.id.txtv);
        //jtvL.append(" " + id + "\t" + nombre + "\t" + padeci + "\t" + dosis + "\t" + doctor + "\n");
        DbmsSQLiteHelper dsqlh = new DbmsSQLiteHelper(this, "DBMedicinas", null, 1);
        sqld = dsqlh.getWritableDatabase();
        ContentValues cv = new ContentValues();
        /*cv.put("id", id);
        cv.put("nombre", nombre);
        cv.put("padecimiento", padeci);
        cv.put("dosis", dosis);
        cv.put("doctor", doctor);
        sqld.insert("Medicamento", null, cv);*/


        jbnL.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Cursor c = sqld.rawQuery("SELECT id,nombre,padecimiento,dosis,doctor FROM Medicamento", null);

                jtvL.setText("");
                if (c.moveToFirst()) {
                    do {
                        id_s= c.getString(0);
                        nombre = c.getString(1);
                        padeci = c.getString(2);
                        dosis = c.getString(3);
                        doctor = c.getString(4);
                        jtvL.append(" " + id_s + "\t" + nombre + "\t" + padeci + "\t" + dosis + "\t" + doctor + "\n");
                    } while(c.moveToNext());
                }
            }
        });
    }
}

