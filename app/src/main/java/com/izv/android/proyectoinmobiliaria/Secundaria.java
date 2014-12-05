package com.izv.android.proyectoinmobiliaria;

/**
 * Created by Ivan on 29/11/2014.
 */
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Secundaria extends Activity {
    private int id;
    private ArrayList<File> listaImg;
    private ImageView ivFoto;
    private int posicion = 0;
    private static int TAKE_PICTURE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secundaria);
        id = getIntent().getExtras().getInt("id");
        final FragmentoDos fdos = (FragmentoDos)getFragmentManager().findFragmentById(R.id.fragment);

        obtenerImagenes(id);
        botonAnterior();
        botonSiguiente();
        eventoFoto();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        finish();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //s=savedInstanceState.getString("eres");
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
        if (id == R.id.anadir) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void obtenerImagenes(int id){
        String directorio = getExternalFilesDir(Environment.DIRECTORY_DCIM).toString();

        File dir = new File(directorio);
        listaImg = new ArrayList<File>();

        File[] fList = dir.listFiles();
        for (File file : fList) {
            String f = file.toString();

            String[] partir = f.split("_");
            System.out.println(partir[1].equals(""+id));
            if(partir[1].equals(""+id)){
                listaImg.add(file);
            }
        }

        if(listaImg.size() > 0){
            mostrarImg(posicion);
        }

    }


    //HAY QUE PRGRAMAR LOS BOTONES Y COMPROBAR QUE VA EL VISOR
    public void mostrarImg(int pos){
        Bitmap image = BitmapFactory.decodeFile("" + listaImg.get(pos));
        Bitmap imgEscalada = Bitmap.createScaledBitmap(image, 500, 500, false);
        ivFoto = (ImageView)findViewById(R.id.ivVisor);
        ivFoto.setImageBitmap(imgEscalada);
    }

    public void botonSiguiente(){
        Button bt = (Button)findViewById(R.id.btSig);
        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                if(posicion+1 > listaImg.size()-1) {
                    posicion = 0;
                }else{
                    posicion = posicion+1;
                }
                mostrarImg(posicion);
            }
        });
    }

    public void botonAnterior(){
        Button bt = (Button)findViewById(R.id.btAnt);
        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                if(posicion-1 < 0) {
                    posicion = listaImg.size()-1;
                }else{
                    posicion = posicion-1;
                }
                mostrarImg(posicion);
            }
        });
    }

    public void eventoFoto(){
        Button bt = (Button)findViewById(R.id.btSubir);
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
}

