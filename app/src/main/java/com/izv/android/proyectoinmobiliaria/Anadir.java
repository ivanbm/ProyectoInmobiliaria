package com.izv.android.proyectoinmobiliaria;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by Ivan on 30/11/2014.
 */
public class Anadir extends MainActivity{
    private Spinner sp;
    private EditText direccion, localidad, precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anadir);

        llenarSpinner();

        eventoBoton();
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
}

