package com.izv.android.proyectoinmobiliaria;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ivan on 30/11/2014.
 */
public class Anadir extends MainActivity{
    private Spinner sp;
    private EditText direccion, localidad, precio;
    private final int TAKE_PICTURE = 3;
    String foto;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anadir);

        llenarSpinner();
        eventoBoton();
        eventoFoto();

        Bundle b = getIntent().getExtras();
        id = b.getInt("id");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.anadir_imagen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.anadir_imagen) {
            anadir();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                tostada(getString(R.string.msgImgSaved));
                break;
            }
        }
    }

    public void llenarSpinner(){
        sp = (Spinner) findViewById(R.id.spTipo);
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this,R.array.array_tipo, android.R.layout.simple_spinner_item);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adaptador);
    }



    public void anadirV(){
        String dir, loc, pre, tipo, dirC;

        direccion = (EditText)findViewById(R.id.etDireccion);
        localidad = (EditText)findViewById(R.id.etLocalidad);
        precio = (EditText)findViewById(R.id.etPrecio);
        sp = (Spinner) findViewById(R.id.spTipo);

        dir = direccion.getText().toString();
        loc = localidad.getText().toString();
        pre = precio.getText().toString();
        dirC = dir + ", " + loc;
        tipo = sp.getSelectedItem().toString();

        Intent result = new Intent();
        result.putExtra("direccion", dirC);
        result.putExtra("tipo", tipo);
        result.putExtra("precio", pre);
        setResult(Activity.RESULT_OK, result);
        finish();

        this.finish();

    }

    public void eventoBoton(){
        Button bt = (Button)findViewById(R.id.btAnadir);
        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                anadirV();
            }
        });
    }

    public void eventoFoto(){
        Button bt = (Button)findViewById(R.id.btFoto);
        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                camara();
            }
        });
    }


    /*-------------------------------------*/
    /*--           HACER FOTO            --*/
    /*-------------------------------------*/

    public void camara(){
        Intent fotoPick = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        String fecha = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
        Uri uriSavedImage=Uri.fromFile(new File(getExternalFilesDir(Environment.DIRECTORY_DCIM),"inmueble_"+id+"_"+fecha+".jpg"));
        fotoPick.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        startActivityForResult(fotoPick,TAKE_PICTURE);

    }

    private void tostada(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}

