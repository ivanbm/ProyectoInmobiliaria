package com.izv.android.proyectoinmobiliaria;

import java.io.Serializable;

/**
 * Created by Ivan on 29/11/2014.
 */
public class Vendedor implements Serializable, Comparable<Vendedor> {
    private int id;
    private String direccion, tipo;
    private double precio;

    public Vendedor(){
        this(0, "", "", 0.0);
    }

    public Vendedor(int id, String direccion, String tipo, double precio) {
        this.id = id;
        this.direccion = direccion;
        this.tipo = tipo;
        this.precio = precio;
    }

    public Vendedor(String direccion, String tipo, String  precio) {
        this.id = id;
        this.direccion = direccion;
        this.tipo = tipo;
        try{
            this.precio = Double.parseDouble(precio);
        }catch (NumberFormatException e){
            this.precio = 0.0;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vendedor vendedor = (Vendedor) o;

        if (id != vendedor.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + direccion.hashCode();
        result = 31 * result + tipo.hashCode();
        temp = Double.doubleToLongBits(precio);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Vendedor{" +
                "id=" + id +
                ", direccion='" + direccion + '\'' +
                ", tipo='" + tipo + '\'' +
                ", precio=" + precio +
                '}';
    }

    @Override
    public int compareTo(Vendedor vendedor) {
        return 0;
    }
}
