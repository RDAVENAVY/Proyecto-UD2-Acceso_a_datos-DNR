package com.mycompany.proyecto_ud2_dnr.Modelo;

public class Seccion {

    private int idSeccion;
    private int idAlmacen;
    private String nombreCategoria;
    private String nombre;
    private String descripcion;
    private int dimensiones;
    private int ocupacion;
    private int capacidad;
    private int capacidadDePale;

    public Seccion() {
    }

    public Seccion(int idSeccion, int idAlmacen, String nombreCategoria, String nombre,
                   String descripcion, int dimensiones, int ocupacion, int capacidad, int capacidadDePale) {
        this.idSeccion = idSeccion;
        this.idAlmacen = idAlmacen;
        this.nombreCategoria = nombreCategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.dimensiones = dimensiones;
        this.ocupacion = ocupacion;
        this.capacidad = capacidad;
        this.capacidadDePale = capacidadDePale;
    }

    public int getIdSeccion() { return idSeccion; }
    public void setIdSeccion(int idSeccion) { this.idSeccion = idSeccion; }

    public int getIdAlmacen() { return idAlmacen; }
    public void setIdAlmacen(int idAlmacen) { this.idAlmacen = idAlmacen; }

    public String getNombreCategoria() { return nombreCategoria; }
    public void setNombreCategoria(String nombreCategoria) { this.nombreCategoria = nombreCategoria; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getDimensiones() { return dimensiones; }
    public void setDimensiones(int dimensiones) { this.dimensiones = dimensiones; }

    public int getOcupacion() { return ocupacion; }
    public void setOcupacion(int ocupacion) { this.ocupacion = ocupacion; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    public int getCapacidadDePale() { return capacidadDePale; }
    public void setCapacidadDePale(int capacidadDePale) { this.capacidadDePale = capacidadDePale; }
}