package com.izv.android.proyectoinmobiliaria;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends Activity {
    private ArrayList<Vendedor> lista;
    private final int SELECT_IMAGE = 0;
    private final int ANADIR_VENDEDOR = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        Vendedor v1 = new Vendedor("Gran via 41, Madrid", "Local", "500.50");
        Vendedor v2 = new Vendedor("Calle Paloma, Bilbao", "Casa", "150.00");
        Vendedor v3 = new Vendedor("Calle Clavel, Sevilla", "Cochera", "120.50");

        lista = new ArrayList<Vendedor>();
        lista.add(v1);
        lista.add(v2);
        lista.add(v3);

        final ListView lv = (ListView) findViewById(R.id.listView);
        Adaptador ad = new Adaptador(this,R.layout.detalle,lista);
        lv.setAdapter(ad);

        final FragmentoDos fdos = (FragmentoDos)getFragmentManager().findFragmentById(R.id.fragment4);
        final boolean horizontal = fdos!=null && fdos.isInLayout(); //Saber que orientación tengo
        int v = getResources().getConfiguration().orientation; //Saber que orientación tengo{
        // }
        /*
        if(v == Configuration.ORIENTATION_LANDSCAPE){}
        if(v == Configuration.ORIENTATION_PORTRAIT){}
        */
        switch (v){

            case Configuration.ORIENTATION_LANDSCAPE:
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                break;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.anadir) {
            anadir();
        }

        return super.onOptionsItemSelected(item);
    }


    /*-------------------------------------*/
    /*--           AÑADIR DISCO          --*/
    /*-------------------------------------*/
    public void anadir(){
        Intent i = new Intent(this,Anadir.class);
        startActivityForResult(i, ANADIR_VENDEDOR);
    }
}
