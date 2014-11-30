package com.izv.android.proyectoinmobiliaria;

import android.provider.BaseColumns;

/**
 * Created by Ivan on 29/11/2014.
 */
public class Contrato {

    private Contrato(){}

    public static abstract class TablaVendedor implements BaseColumns{
        public static final String TABLA = "vendedor";
        public static final String DIRECCION = "direccion";
        public static final String TIPO = "tipo";
        public static final String PRECIO = "precio";
    }
}