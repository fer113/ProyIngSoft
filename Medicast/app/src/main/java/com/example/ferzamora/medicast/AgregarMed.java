package com.example.ferzamora.medicast;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AgregarMed extends AppCompatActivity {
    private static final int CAMERA_PERMISSION= 1 ;
    Button btn, btn2, btn3;
    int id=0;
    ImageView img, img2;
    EditText txt1, txt2, txt3, txt4;
    Intent i;
    SQLiteDatabase sqld;
    boolean cual;
    final static int cons=0;
    Bitmap bmp;
    int permiso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DbmsSQLiteHelper dsqlh = new DbmsSQLiteHelper(this, "DBMedicinas", null, 1);
        sqld = dsqlh.getWritableDatabase();
        init();
        setContentView(R.layout.activity_agregar_med);
        txt1 = (EditText) findViewById(R.id.textNombre);
        txt2 = (EditText) findViewById(R.id.textPadecimiento);
        txt3 = (EditText) findViewById(R.id.textDosis);
        txt4 = (EditText) findViewById(R.id.textDias);
        btn=(Button)findViewById(R.id.BtnFoto);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cual = true;
                i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,cons);
            }
        });
        img=(ImageView)findViewById(R.id.imagen);
        btn2=(Button)findViewById(R.id.BtnFoto2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cual = false;
                i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,cons);
            }
        });
        img2=(ImageView)findViewById(R.id.imagen2);
        btn3 = (Button)findViewById(R.id.BtnAceptar);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = txt1.getText().toString();
                String padeci = txt2.getText().toString();
                String dosis = txt3.getText().toString();
                String doctor = txt4.getText().toString();
                int idint=0;
                ContentValues cv = new ContentValues();
                if (nombre.isEmpty()!=true){
                    Cursor c = sqld.rawQuery("SELECT id,nombre,padecimiento,dosis,doctor FROM Medicamento", null);
                    if(c.moveToFirst()) {
                        do {
                            idint = Integer.parseInt(c.getString(0));
                            if(idint==id){
                                id++;
                                return;
                            }
                        } while (c.moveToNext());
                    }
                    /*cv.put("id", id);
                    cv.put("nombre", nombre);
                    cv.put("padecimiento", padeci);
                    cv.put("dosis", dosis);
                    cv.put("doctor", doctor);
                    sqld.insert("Medicamento", null, cv);*/
                    txt1.setText("");
                    txt2.setText("");
                    txt3.setText("");
                    txt4.setText("");

                    Intent siguiente =new Intent(AgregarMed.this , Horario.class);

                    siguiente.putExtra("id", id);
                    siguiente.putExtra("nombre", nombre);
                    siguiente.putExtra("padecimiento", padeci);
                    siguiente.putExtra("dosis", dosis);
                    siguiente.putExtra("doctor", doctor);
                    startActivity(siguiente);
                }else{
                    txt4.setText("Debe llenar todos los datos");
                }
            }
        });
    }

    public void init(){
        permiso= ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        CAMERA_PERMISSION);

            }
        }

    }

    @Override
    protected void onActivityResult(int RequstCode, int ResultCode,Intent data){
        super.onActivityResult(RequstCode, ResultCode,data);
        if(ResultCode== Activity.RESULT_OK){
            Bundle ext=data.getExtras();
            bmp=(Bitmap)ext.get("data");
            if(cual==true) {
                img.setImageBitmap(bmp);
            }else{
                img2.setImageBitmap(bmp);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length==1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Acceso a camara concedido", Toast.LENGTH_SHORT).show();
                } else {

                }
                return;
            }
        }
    }
}
