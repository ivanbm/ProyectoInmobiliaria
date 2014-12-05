package com.izv.android.proyectoinmobiliaria;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


public class MainActivity extends Activity {
    private ArrayList<Vendedor> lista;
    private final int ANADIR_VENDEDOR = 1;
    private final int MOSTRAR_FOTOS = 2;
    private final int TAKE_PICTURE = 3;
    private Adaptador ad;
    private Spinner sp;
    private ListView lv;
    private String foto;
    private int id;
    private ArrayList<File> listaImg;
    private ImageView ivFoto;
    private int posicion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);


        initComponents();
        final FragmentoDos fdos = (FragmentoDos) getFragmentManager().findFragmentById(R.id.fragment4);
        final boolean horizontal = fdos != null && fdos.isInLayout(); //Saber que orientación tengo
        int v = getResources().getConfiguration().orientation; //Saber que orientación tengo{
        // }
        /*
        if(v == Configuration.ORIENTATION_LANDSCAPE){}
        if(v == Configuration.ORIENTATION_PORTRAIT){}
        */
        switch (v) {

            case Configuration.ORIENTATION_LANDSCAPE:
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                break;
        }

        lv = (ListView)findViewById(R.id.listView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(horizontal){
                    obtenerImagenes(lista.get(position).getId());
                    eventoFoto();
                    botonAnterior();
                    botonSiguiente();
                }else{
                    Intent i = new Intent(MainActivity.this,Secundaria.class);
                    i.putExtra("id",lista.get(position).getId());
                    startActivityForResult(i,MOSTRAR_FOTOS);
                }
            }
        });
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        switch (item.getItemId()){

            case R.id.editar:
                editar(index);
                ad.notifyDataSetChanged();

                return true;

            case R.id.elimiar:
                eliminar(index);
                ad.notifyDataSetChanged();

                return true;
            default:
                return super.onOptionsItemSelected(item);
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


    /*----------------------------------------------------*/
    /*                  SELECCIONAR IMAGENES              */
    /*----------------------------------------------------*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    tostada(getString(R.string.msgImgSaved));
                    break;

                case ANADIR_VENDEDOR:
                    String direccion, tipo, precio;
                    int id;
                    Bundle ven = data.getExtras();
                    direccion = ven.getString("direccion");
                    tipo = ven.getString("tipo");
                    precio = ven.getString("precio");

                    int tam = lista.size();
                    if(tam == 0){
                        id=0;
                    }else{
                        id = lista.get(tam-1).getId()+1;
                    }

                    lista.add(new Vendedor(id, direccion, tipo, Double.parseDouble(precio)));
                    System.out.println("TAMAÑO "+lista.size());
                    ClaseXML cxml = new ClaseXML();
                    cxml.nuevoArchivo(getApplicationContext(),lista);
                    lista = cxml.leer(getApplicationContext());

                    mostrarInmuebles();
                    ad.notifyDataSetChanged();
                    tostada(getString(R.string.msgAnadir));
                    break;
            }
        } else {
            //coverSeleccionada = false;
        }
    }


    /*-------------------------------------*/
    /*--          AÑADIR INMUEBLE        --*/
    /*-------------------------------------*/
    public void anadir() {
        Intent i = new Intent(this, Anadir.class);
        Collections.sort(lista);
        int id;
        if(lista.size() > 0) {
            id = lista.get(lista.size() - 1).getId() + 1;
        }else{
            id = 0;
        }
        i.putExtra("id", id);
        startActivityForResult(i, ANADIR_VENDEDOR);
    }

    /*-------------------------------------*/
    /*--        MOSTRAR INMUEBLES        --*/
    /*-------------------------------------*/

    private void mostrarInmuebles() {
        final ListView lv = (ListView) findViewById(R.id.listView);
        ad = new Adaptador(this, R.layout.detalle, lista);
        lv.setAdapter(ad);
        registerForContextMenu(lv);
    }

    /*-------------------------------------*/
    /*--          EDITAR INMUEBLE        --*/
    /*-------------------------------------*/

    public boolean editar(final int index){
        final int id = lista.get(index).getId();
        final String dir = lista.get(index).getDireccion();
        final String tip = lista.get(index).getTipo();
        final double pre = lista.get(index).getPrecio();


        final AlertDialog.Builder alert= new AlertDialog.Builder(this);
        alert.setTitle(R.string.tituloEditar);

        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.editar, null);
        alert.setView(vista);

        final EditText et1, et2, et3, et4;

        et1=(EditText)vista.findViewById(R.id.etDireccionE);
        et2=(EditText)vista.findViewById(R.id.etPrecioE);
        et3=(EditText)vista.findViewById(R.id.etTipo);
        et4=(EditText)vista.findViewById(R.id.etId);

        et1.setText(dir);
        et2.setText(""+pre);
        et3.setText(tip);
        et4.setText(""+id);

        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()

                {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Vendedor vOld = new Vendedor(id, dir, tip, pre);
                        Vendedor vNuevo = new Vendedor(Integer.parseInt("" + et4.getText()), et1.getText().toString(), et3.getText().toString(), Double.parseDouble(et2.getText().toString()));

                        Collections.sort(lista);
                        ClaseXML cxml = new ClaseXML();
                        cxml.modificar(getApplicationContext(), lista, vNuevo, vOld);

                        ad.notifyDataSetChanged();
                        tostada(getString(R.string.msgEditar));
                        mostrarInmuebles();
                    }
                }

        );
    alert.setNegativeButton(android.R.string.no,null);
    alert.show();
        return true;
}

    /*-------------------------------------*/
    /*--        ELIMINAR INMUEBLE        --*/
    /*-------------------------------------*/

    public void eliminar(final int index){
        int id = lista.get(index).getId();
        String direccion = lista.get(index).getDireccion();
        String tipo = lista.get(index).getTipo();
        double precio = lista.get(index).getPrecio();
        Vendedor v = new Vendedor(id, direccion, tipo, precio);

        ClaseXML cxml = new ClaseXML();
        cxml.eliminar(getApplicationContext(), lista, v);

        Collections.sort(lista);
        tostada(getString(R.string.msgEliminar));
        mostrarInmuebles();
    }


    private void tostada(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    /*-------------------------------------*/
    /*--        LLENAR SPINNER           --*/
    /*-------------------------------------*/


    /*public void llenarSpinner(){
        sp = new Spinner(getApplicationContext(), Spinner.MODE_DIALOG);
        //setContentView(R.layout.editar);
        sp = (Spinner) findViewById(R.id.spTipo);
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this,R.array.array_tipo, android.R.layout.simple_spinner_item);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adaptador);
    }*/

    private void initComponents(){
        lista = new ArrayList<Vendedor>();

        ClaseXML cxml = new ClaseXML();
        lista = cxml.leer(getApplicationContext());

        mostrarInmuebles();
    }

    /*-------------------------------------*/
    /*--        METODOS DEL VISOT        --*/
    /*-------------------------------------*/

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
