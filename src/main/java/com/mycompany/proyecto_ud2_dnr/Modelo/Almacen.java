package com.mycompany.proyecto_ud2_dnr.Modelo;

public class Almacen {

    private int idAlmacen;
    private int idTrabajador;
    private String nombre;
    private String descripcion;
    private String dimensiones;
    private int ocupacion;
    private int capacidadDePale;
    private String direccion;

    public Almacen() {
    }

    public Almacen(int idAlmacen, int idTrabajador, String nombre, String descripcion,
                   String dimensiones, int ocupacion, int capacidadDePale, String direccion) {
        this.idAlmacen = idAlmacen;
        this.idTrabajador = idTrabajador;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.dimensiones = dimensiones;
        this.ocupacion = ocupacion;
        this.capacidadDePale = capacidadDePale;
        this.direccion = direccion;
    }

    public int getIdAlmacen() { return idAlmacen; }
    public void setIdAlmacen(int idAlmacen) { this.idAlmacen = idAlmacen; }

    public int getIdTrabajador() { return idTrabajador; }
    public void setIdTrabajador(int idTrabajador) { this.idTrabajador = idTrabajador; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getDimensiones() { return dimensiones; }
    public void setDimensiones(String dimensiones) { this.dimensiones = dimensiones; }

    public int getOcupacion() { return ocupacion; }
    public void setOcupacion(int ocupacion) { this.ocupacion = ocupacion; }

    public int getCapacidadDePale() { return capacidadDePale; }
    public void setCapacidadDePale(int capacidadDePale) { this.capacidadDePale = capacidadDePale; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}
