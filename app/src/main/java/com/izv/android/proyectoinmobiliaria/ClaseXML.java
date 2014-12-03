package com.izv.android.proyectoinmobiliaria;

import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Ivan on 16/11/2014.
 */
public class ClaseXML {

    public static void nuevoArchivo(Context contexto, ArrayList<Vendedor> lista){
        try{
            FileOutputStream fosxml = new FileOutputStream(new File(contexto.getFilesDir(),"inmobiliaria.xml"));

            XmlSerializer docxml= Xml.newSerializer();
            docxml.setOutput(fosxml, "UTF-8");
            docxml.startDocument(null, Boolean.valueOf(true));
            docxml.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

            docxml.startTag(null, "inmobiliaria");
            for(int i = 0; i<lista.size();i++){
                //System.out.println("ESCRIBIENDO "+lista.get(i).getPrecio());
                docxml.startTag(null, "inmueble");
                docxml.attribute(null, "id", "" + lista.get(i).getId());
                docxml.attribute(null, "direccion", lista.get(i).getDireccion());
                docxml.attribute(null, "tipo", lista.get(i).getTipo());
                docxml.attribute(null, "precio", ""+lista.get(i).getPrecio());
                docxml.endTag(null, "inmueble");
            }
            docxml.endTag(null, "inmobiliaria");
            docxml.endDocument();
            docxml.flush();
            fosxml.close();
        }catch(Exception e){
            System.out.println("ERROR AL ESCRIBIR "+e);
        }
    }

    public static void eliminar(Context con, ArrayList<Vendedor> lista , Vendedor v){
        for(int i=0;i<lista.size();i++){
            if(lista.get(i).equals(v)){
                lista.remove(i);
                nuevoArchivo(con, lista);
                Collections.sort(lista);
                break;
            }
        }

    }

    public static void modificar(Context con, ArrayList<Vendedor> lista, Vendedor vNuevo, Vendedor vOld){
        for(int i=0;i<lista.size();i++){
            if(lista.get(i).equals(vOld)){
                lista.remove(i);
                lista.add(vNuevo);
                nuevoArchivo(con, lista);
                Collections.sort(lista);
                break;
            }
        }
    }

    public static ArrayList<Vendedor> leer(Context contexto){
        ArrayList<Vendedor> lista = new ArrayList<Vendedor>();

        try{
            XmlPullParser lectorxml= Xml.newPullParser();
            lectorxml.setInput(new FileInputStream(new File(contexto.getFilesDir(),"inmobiliaria.xml")),"utf-8");
            int evento = lectorxml.getEventType();

            while(evento!= XmlPullParser.END_DOCUMENT){
                if(evento== XmlPullParser.START_TAG){
                    String etiqueta = lectorxml.getName();
                    if(etiqueta.compareTo("inmueble")==0){
                        //System.out.println("LEYENDO "+lectorxml.getAttributeValue(null, "direccion"));
                        lista.add(new Vendedor(Integer.parseInt(lectorxml.getAttributeValue(null, "id")),
                                lectorxml.getAttributeValue(null, "direccion"),
                                lectorxml.getAttributeValue(null, "tipo"),
                                Double.parseDouble(lectorxml.getAttributeValue(null, "precio"))
                        ));
                    }
                }
                evento = lectorxml.next();
            }

        }catch (Exception e) {
            System.out.println("ERROR AL LEER "+e);
        }

        if(lista.size()!=0){
            return lista;
        }else{
            return new ArrayList<Vendedor>();
        }

    }

}
