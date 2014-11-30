package com.izv.android.proyectoinmobiliaria;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ivan on 29/11/2014.
 */
public class Ayudante extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "inmobiliaria.sqlite";
    public static final int DATABASE_VERSION = 1;

    public Ayudante(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        sql = "create table "+Contrato.TablaVendedor.TABLA+
                " ("+Contrato.TablaVendedor._ID+
                " integer primary key autoincrement, "+
                Contrato.TablaVendedor.DIRECCION+" text, "+
                Contrato.TablaVendedor.TIPO+" text, "+
                Contrato.TablaVendedor.PRECIO+" double)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Trandormar el esquema de la versión old a la versión new sin que se produzca ninguna pérdida de datos.
        //1º crear tablas de respaldo (idénticas)
        //2º copiar los datos a esas tablas
        //3º borrar las tablas originales
        //4º crear las tablas nuevas (onCreate)
        //5º copiar los datos de respaldo en mis tablas
        //6º borrar las tablas de respaldo
        String sql="drop table if exists " + Contrato.TablaVendedor.TABLA;
        db.execSQL(sql);
        onCreate(db);

    }
}
