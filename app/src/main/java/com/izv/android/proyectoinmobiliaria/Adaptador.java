package com.izv.android.proyectoinmobiliaria;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Ivan on 29/11/2014.
 */
public class Adaptador extends ArrayAdapter<Vendedor> {

    private ArrayList<Vendedor> lista;
    private Context contexto;
    private int recurso;
    private static LayoutInflater i;

    public Adaptador(Context context, int resource, ArrayList<Vendedor> objects) {
        super(context, resource, objects);
        this.contexto = context;
        this.lista = objects;
        this.recurso = resource;
        this.i = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static class ViewHolder{
        public TextView tv1, tv2, tv3;
        public ImageView iv;
        public int position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if(convertView == null){
            convertView = i.inflate(recurso, null);

            //ImageView iv1 = (ImageView)convertView.findViewById(R.id.ivImagen);
            vh = new ViewHolder();
            vh.tv1 = (TextView)convertView.findViewById(R.id.tvDireccion);
            vh.tv2 = (TextView)convertView.findViewById(R.id.tvTipo);
            vh.tv3 = (TextView)convertView.findViewById(R.id.tvPrecio);

            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }

        vh.position = position;
        vh.tv1.setTag(position);
        //Log.v("LOG",vh.tv1.getTag().toString());
        vh.tv1.setText(lista.get(position).getDireccion());
        vh.tv2.setText(lista.get(position).getTipo());
        vh.tv3.setText(""+lista.get(position).getPrecio()+" €");

        /*if(lista.get(position).getImagen().equals("vacio")) {
            vh.iv.setImageResource(R.drawable.ic_launcher);
            System.out.println("ENTRA");
        }else{
            Bitmap bitmap = BitmapFactory.decodeFile(lista.get(position).getImagen());
            Bitmap caratula = Bitmap.createScaledBitmap(bitmap, 200, 200, false);
            caratula = getRoundedCornerBitmap(caratula);
            vh.iv.setImageBitmap(caratula);
        }
        vh.iv.setTag(position);*/

        return convertView;
    }
}