package com.izv.android.proyectoinmobiliaria;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 29/11/2014.
 */
public class GestionVendedor   {
    private Ayudante abd;
    private SQLiteDatabase bd;

    public GestionVendedor(Context c){
        abd = new Ayudante(c);
    }

    public void open(){
        bd = abd.getWritableDatabase();
    }
    public void openRead(){
        bd = abd.getReadableDatabase();
    }
    public void close(){
        abd.close();
    }

    public long insert(Vendedor vendedor){
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaVendedor.DIRECCION, vendedor.getDireccion());
        valores.put(Contrato.TablaVendedor.TIPO, vendedor.getTipo());
        valores.put(Contrato.TablaVendedor.PRECIO, vendedor.getPrecio());
        long id = bd.insert(Contrato.TablaVendedor.TABLA, null, valores);

        return id;
    }

    public int delete(Vendedor vendedor){
        String condicion = Contrato.TablaVendedor._ID + " = ?";
        String[] argumentos = { vendedor.getId() + "" };
        int cuenta = bd.delete(Contrato.TablaVendedor.TABLA, condicion, argumentos);
        return cuenta;
    }

    public int delete(int id){
        return delete(new Vendedor(id, null, null, 0.0));
    }

    public List<Vendedor> select(String condicion, String[] parametros, String orden) {
        List<Vendedor> lo = new ArrayList<Vendedor>();
        Cursor cursor = bd.query(Contrato.TablaVendedor.TABLA, null, condicion, parametros, null, null, orden);
        /*
        String[] campos = {"nombre", "valoracion"};
        String[] parametros = {"Pepe", "6"};
        bd.query("tabla", campos, "nombre = ? and valoracion = ?", parametros, "groupBy", "having", "orderBy");
        //select nombre, valoracion from tabla
        //where nombre = ? and valoracion = ?
        //...
        */
        cursor.moveToFirst();
        Vendedor objeto;
        while (!cursor.isAfterLast()) {
            objeto = getRow(cursor);
            lo.add(objeto);
            cursor.moveToNext();
        }
        cursor.close();
        return lo;
    }

    public ArrayList<Vendedor> select() {
        ArrayList<Vendedor> lo = new ArrayList<Vendedor>();
        Cursor cursor = bd.query(Contrato.TablaVendedor.TABLA, null, null, null, null, null, null);
        cursor.moveToFirst();
        Vendedor objeto;
        while (!cursor.isAfterLast()) {
            objeto = getRow(cursor);
            lo.add(objeto);
            cursor.moveToNext();
        }
        cursor.close();
        return lo;
    }

    public static Vendedor getRow(Cursor c) {
        Vendedor objeto = new Vendedor();
        objeto.setId(c.getLong(0));
        objeto.setDireccion(c.getString(1));
        objeto.setTipo(c.getString(2));
        objeto.setPrecio(c.getDouble(3));
        return objeto;
    }

    public Vendedor getRow(long id){
        List<Vendedor> vendedor = select(Contrato.TablaVendedor._ID + " = ?", new String[]{id + ""}, null);
        Vendedor objeto = vendedor.get(0);
        if (!vendedor.isEmpty())
            return objeto;
        else
            return null;
    }

    public Cursor getCursor(String condicion, String[] parametros, String orden) {
        Cursor cursor = bd.query(Contrato.TablaVendedor.TABLA, null, condicion, parametros, null, null, orden);
        return cursor;
    }

    public Cursor getCursor() {
        Cursor cursor = bd.query(Contrato.TablaVendedor.TABLA, null, null, null, null, null, null);
        return cursor;
    }

    public Vendedor getRow(String tipo) {
        String[] parametros = new String[] { tipo };
        Cursor c = bd.rawQuery("select * from "+
                Contrato.TablaVendedor.TABLA
                + " where " + Contrato.TablaVendedor.TIPO + " = ?", parametros);
        c.moveToFirst();
        Vendedor objeto = getRow(c);
        c.close();
        return objeto;
    }

}
